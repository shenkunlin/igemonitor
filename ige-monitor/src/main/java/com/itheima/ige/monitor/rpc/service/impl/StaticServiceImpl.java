package com.itheima.ige.monitor.rpc.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itheima.ige.framework.util.Result;
import com.itheima.ige.monitor.mapper.FullStaticallyMapper;
import com.itheima.ige.monitor.mapper.IncrementStaticallyMapper;
import com.itheima.ige.monitor.mapper.ServerConfigMapper;
import com.itheima.ige.monitor.pojo.FullStatically;
import com.itheima.ige.monitor.pojo.IncrementStatically;
import com.itheima.ige.monitor.pojo.ServerConfig;
import com.itheima.ige.monitor.rpc.service.StaticService;
import com.itheima.ige.template.TemplateProcessor;
import com.itheima.ige.util.IgeConstant;
import com.itheima.ige.wrap.IgeAttribute;
import com.itheima.ige.wrap.ServerInfoConfig;
import com.itheima.storage.data.FileData;
import com.itheima.storage.data.RespData;
import com.itheima.storage.service.FileHandler;
import com.itheima.storage.service.LoadAllServerConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StaticServiceImpl implements StaticService {

    @Autowired
    private IncrementStaticallyMapper incrementStaticallyMapper;

    @Autowired
    private FullStaticallyMapper fullStaticallyMapper;

    @Autowired
    @Qualifier("freemarkerProcessor")
    private TemplateProcessor templateProcessor;

    @Autowired
    private ServerConfigMapper serverConfigMapper;

    @Autowired
    private LoadAllServerConf loadAllServerConf;

    @Autowired
    private FileHandler fastDFSFileHandler;

    @Autowired
    private FileHandler aliyunOssFileHandler;

    @Autowired
    private FileHandler minIoFileHandler;

    @Override
    public String test(String name){
        return "hello!";
    }

    /***
     * 获取所有全量服务
     * @param namespace
     * @return
     */
    @Override
    public String loadAllFullJobs(String namespace) {
        //查询指定项目的全量任务，单个项目最多支持30个批量任务
        LambdaQueryWrapper<FullStatically> wrapper = Wrappers
                .<FullStatically>lambdaQuery()
                .eq(FullStatically::getNamespaceName, namespace)
                .eq(FullStatically::getStatus, 1)
                .last(" limit 30");
        List<FullStatically> fullStaticallies =
                fullStaticallyMapper.selectList(wrapper);

        //模板数据清理掉
        for (FullStatically fullStatically : fullStaticallies) {
            fullStatically.setTemplate("");
        }
        //将全量任务返回
        return Result.ok(fullStaticallies).json();
    }


    /****
     * 删除文件
     * @param url
     * @return
     * @throws Exception
     */
    @Override
    public String del(String url) throws Exception {
        //根据url获取服务信息
        ServerInfoConfig serverInfoConfig = loadAllServerConf.matchServer(url);
        if(serverInfoConfig==null){
            return Result.error("无法正确找到该文件位置！").json();
        }
        //根据服务信息实现文件删除
        if(serverInfoConfig.getType()==IgeConstant.OUTTYPE_FASTDFS){
            //构建fileData
            FileData fileData = FileData.builder()
                    .url(url)
                    .id(serverInfoConfig.getId())
                    .metaData(serverInfoConfig.getConfig()).build();
            RespData respData = fastDFSFileHandler.del(fileData);
            return respData.json();
        }

        if(serverInfoConfig.getType()==IgeConstant.OUTTYPE_ALIYUNOSS){
            //构建fileData
            FileData fileData = FileData.builder()
                    .url(url)
                    .id(serverInfoConfig.getId())
                    .metaData(serverInfoConfig.getConfig()).build();
            RespData respData = aliyunOssFileHandler.del(fileData);
            return respData.json();
        }

        if(serverInfoConfig.getType()==IgeConstant.OUTTYPE_MINIO){
            //构建fileData
            FileData fileData = FileData.builder()
                    .url(url)
                    .id(serverInfoConfig.getId())
                    .metaData(serverInfoConfig.getConfig()).build();
            RespData respData = minIoFileHandler.del(fileData);
            return respData.json();
        }
        return RespData.builder().code(RespData.SUCCESS_CODE).message("无法正确找到该文件位置！").build().json();
    }

    /**
     * 批量操作
     * @param igeAttributes
     * @return
     */
    @Override
    public String fullGenerateBatch(List<IgeAttribute> igeAttributes) throws Exception {
        if(igeAttributes!=null && igeAttributes.size()>0){
            //防止JSONObject转换失败
            //igeAttributes = JSON.parseArray(igeAttributes.toString(),IgeAttribute.class);
            //1)数据检测
            IgeAttribute igeAttr = igeAttributes.get(0);
            FullStatically fullStatically = fullQueryConfig(igeAttr);
            if(fullStatically==null){
                return Result.error("不存在对应的全量静态化任务！").json();
            }
            if(fullStatically.getStatus()==IgeConstant.STATUS_UTE_2){
                return Result.error("请先开启该全量静态化任务！").json();
            }

            //2)查询存储服务
            ServerConfig serverConfig =
                    fullStatically.getOutType().intValue()== IgeConstant.INCREMENTSTATICALLY_OUTTYPE_RETURN?
                            null : serverConfigMapper.selectOne(Wrappers.<ServerConfig>lambdaQuery().eq(ServerConfig::getId, fullStatically.getStorageType()));

            //3)上传
            return process(igeAttributes,serverConfig,fullStatically.getTemplate(),fullStatically.getOutType(),fullStatically.getStorageType());
        }
        return Result.error("静态页生成未能正确执行！").json();
    }

    /***
     * 单个生成
     * @param igeAttribute
     * @return
     * @throws Exception
     */
    @Override
    public String generateOne(IgeAttribute igeAttribute) throws Exception {
        IncrementStatically incrementStatically = queryConfig(igeAttribute);
        if(incrementStatically==null){
            return Result.error("不存在对应的增量静态化配置！").json();
        }
        if(incrementStatically.getStatus()==IgeConstant.STATUS_UTE_2){
            return Result.error("请先开启该增量静态化功能！").json();
        }
        ServerConfig serverConfig =
                incrementStatically.getOutType().intValue()== IgeConstant.INCREMENTSTATICALLY_OUTTYPE_RETURN?
                        null : serverConfigMapper.selectOne(Wrappers.<ServerConfig>lambdaQuery().eq(ServerConfig::getId, incrementStatically.getStorageType()));
        //生成页面
        String result =
                generate(
                        igeAttribute,
                        incrementStatically.getTemplate(),
                        incrementStatically.getOutType(),
                        incrementStatically.getStorageType(),
                        serverConfig);
        if(!ObjectUtils.isEmpty(result)){
            Map<String,String> resultMap = new HashMap<String,String>();
            resultMap.put(igeAttribute.getId(),result);
            return Result.ok(resultMap).json();
        }
        return Result.error("静态页生成未能正确执行！").json();
    }

    /**
     * 批量操作
     * @param igeAttributes
     * @return
     */
    @Override
    public String generateBatch(List<IgeAttribute> igeAttributes) throws Exception {
        if(igeAttributes!=null && igeAttributes.size()>0){
            //防止JSONObject转换失败
            //igeAttributes = JSON.parseArray(igeAttributes.toString(),IgeAttribute.class);
            IgeAttribute igeAttr = igeAttributes.get(0);
            IncrementStatically incrementStatically = queryConfig(igeAttr);
            if(incrementStatically==null){
                return Result.error("不存在对应的增量静态化配置！").json();
            }
            if(incrementStatically.getStatus()==IgeConstant.STATUS_UTE_2){
                return Result.error("请先开启该增量静态化功能！").json();
            }

            //批量生成
            ServerConfig serverConfig =
                    incrementStatically.getOutType().intValue()== IgeConstant.INCREMENTSTATICALLY_OUTTYPE_RETURN?
                    null : serverConfigMapper.selectOne(Wrappers.<ServerConfig>lambdaQuery().eq(ServerConfig::getId, incrementStatically.getStorageType()));
            return process(igeAttributes,serverConfig,incrementStatically.getTemplate(),incrementStatically.getOutType(),incrementStatically.getStorageType());
        }
        return Result.error("静态页生成未能正确执行！").json();
    }

    /**
     * 增量配置查询
     * @param igeAttr
     * @return
     */
    private IncrementStatically queryConfig(IgeAttribute igeAttr) {
        return incrementStaticallyMapper.selectOne(Wrappers.<IncrementStatically>lambdaQuery()
                .eq(IncrementStatically::getUniqueId,igeAttr.getUniqueId())
                .eq(IncrementStatically::getNamespaceName,igeAttr.getNamespace()));
    }

    /**
     * 全量配置查询
     * @param igeAttr
     * @return
     */
    private FullStatically fullQueryConfig(IgeAttribute igeAttr) {
        return fullStaticallyMapper.selectOne(Wrappers.<FullStatically>lambdaQuery()
                .eq(FullStatically::getUniqueId,igeAttr.getUniqueId())
                .eq(FullStatically::getNamespaceName,igeAttr.getNamespace()));
    }

    /***
     * 生成页面处理
     * @param igeAttributes
     * @param serverConfig
     * @param template
     * @param outType
     * @param storageType
     * @return
     * @throws Exception
     */
    public String process(List<IgeAttribute> igeAttributes,ServerConfig serverConfig,String template,int outType,String storageType) throws Exception {
        //3)循环上传
        Map<String,String> resultMap = new HashMap<String,String>();
        for (IgeAttribute igeAttribute : igeAttributes) {

            //生成页面
            String result =
                    generate(
                            igeAttribute,
                            template,
                            outType,
                            storageType,
                            serverConfig);
            if(!ObjectUtils.isEmpty(result)){
                resultMap.put(igeAttribute.getId(),result);
            }
        }
        //5)返回
        return Result.ok(resultMap).json();
    }

    /***
     * 生成文件
     */
    public String generate(IgeAttribute igeAttribute,String template,int outType,String storageType,ServerConfig serverConfig) throws Exception {
        //1)生成静态页
        String content = templateProcessor.generate(template,igeAttribute.getData());

        //2)直接返回模式返回生成的内容
        if(outType== IgeConstant.INCREMENTSTATICALLY_OUTTYPE_RETURN){
            return content;
        }

        //3)基于FastDFS存储，返回url
        if(outType==IgeConstant.INCREMENTSTATICALLY_OUTTYPE_FASTDFS){
            //适配FastDFS
            FileData fileData = FileData.builder()
                    .id(storageType)
                    .ext(igeAttribute.getExt())
                    .bytes(content.getBytes(Charset.forName(IgeConstant.ENCODING_UTF8)))
                    .metaData(JSON.parseObject(serverConfig.getConfigContent(),Map.class))
                    .build();
            //上传至FastDFS
            RespData respData = fastDFSFileHandler.upload(fileData);
            return respData.getUrl();
        }

        //4)采用AliyunOSS存储
        if(outType==IgeConstant.INCREMENTSTATICALLY_OUTTYPE_ALIYUNOSS){
            //适配AliyunOSS
            FileData fileData = FileData.builder()
                    .ext(igeAttribute.getExt())
                    .fileName(igeAttribute.getFileName())
                    .bytes(content.getBytes())
                    .metaData(JSON.parseObject(serverConfig.getConfigContent(),Map.class))
                    .build();
            //上传至AliyunOSS
            RespData respData = aliyunOssFileHandler.upload(fileData);
            return respData.getUrl();
        }

        //5)采用MinIO存储
        if(outType==IgeConstant.INCREMENTSTATICALLY_OUTTYPE_MINIO){
            //适配MinIO
            FileData fileData = FileData.builder()
                    .id(igeAttribute.getId())
                    .ext(igeAttribute.getExt())
                    .fileName(igeAttribute.getFileName())
                    .bytes(content.getBytes())
                    .metaData(JSON.parseObject(serverConfig.getConfigContent(),Map.class))
                    .build();
            //上传至MinIO
            RespData respData = minIoFileHandler.upload(fileData);
            return respData.getUrl();
        }

        //如果无法匹配，直接返回空
        return null;
    }
}
