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
     * ????????????????????????
     * @param namespace
     * @return
     */
    @Override
    public String loadAllFullJobs(String namespace) {
        //????????????????????????????????????????????????????????????30???????????????
        LambdaQueryWrapper<FullStatically> wrapper = Wrappers
                .<FullStatically>lambdaQuery()
                .eq(FullStatically::getNamespaceName, namespace)
                .eq(FullStatically::getStatus, 1)
                .last(" limit 30");
        List<FullStatically> fullStaticallies =
                fullStaticallyMapper.selectList(wrapper);

        //?????????????????????
        for (FullStatically fullStatically : fullStaticallies) {
            fullStatically.setTemplate("");
        }
        //?????????????????????
        return Result.ok(fullStaticallies).json();
    }


    /****
     * ????????????
     * @param url
     * @return
     * @throws Exception
     */
    @Override
    public String del(String url) throws Exception {
        //??????url??????????????????
        ServerInfoConfig serverInfoConfig = loadAllServerConf.matchServer(url);
        if(serverInfoConfig==null){
            return Result.error("????????????????????????????????????").json();
        }
        //????????????????????????????????????
        if(serverInfoConfig.getType()==IgeConstant.OUTTYPE_FASTDFS){
            //??????fileData
            FileData fileData = FileData.builder()
                    .url(url)
                    .id(serverInfoConfig.getId())
                    .metaData(serverInfoConfig.getConfig()).build();
            RespData respData = fastDFSFileHandler.del(fileData);
            return respData.json();
        }

        if(serverInfoConfig.getType()==IgeConstant.OUTTYPE_ALIYUNOSS){
            //??????fileData
            FileData fileData = FileData.builder()
                    .url(url)
                    .id(serverInfoConfig.getId())
                    .metaData(serverInfoConfig.getConfig()).build();
            RespData respData = aliyunOssFileHandler.del(fileData);
            return respData.json();
        }

        if(serverInfoConfig.getType()==IgeConstant.OUTTYPE_MINIO){
            //??????fileData
            FileData fileData = FileData.builder()
                    .url(url)
                    .id(serverInfoConfig.getId())
                    .metaData(serverInfoConfig.getConfig()).build();
            RespData respData = minIoFileHandler.del(fileData);
            return respData.json();
        }
        return RespData.builder().code(RespData.SUCCESS_CODE).message("????????????????????????????????????").build().json();
    }

    /**
     * ????????????
     * @param igeAttributes
     * @return
     */
    @Override
    public String fullGenerateBatch(List<IgeAttribute> igeAttributes) throws Exception {
        if(igeAttributes!=null && igeAttributes.size()>0){
            //??????JSONObject????????????
            //igeAttributes = JSON.parseArray(igeAttributes.toString(),IgeAttribute.class);
            //1)????????????
            IgeAttribute igeAttr = igeAttributes.get(0);
            FullStatically fullStatically = fullQueryConfig(igeAttr);
            if(fullStatically==null){
                return Result.error("??????????????????????????????????????????").json();
            }
            if(fullStatically.getStatus()==IgeConstant.STATUS_UTE_2){
                return Result.error("???????????????????????????????????????").json();
            }

            //2)??????????????????
            ServerConfig serverConfig =
                    fullStatically.getOutType().intValue()== IgeConstant.INCREMENTSTATICALLY_OUTTYPE_RETURN?
                            null : serverConfigMapper.selectOne(Wrappers.<ServerConfig>lambdaQuery().eq(ServerConfig::getId, fullStatically.getStorageType()));

            //3)??????
            return process(igeAttributes,serverConfig,fullStatically.getTemplate(),fullStatically.getOutType(),fullStatically.getStorageType());
        }
        return Result.error("????????????????????????????????????").json();
    }

    /***
     * ????????????
     * @param igeAttribute
     * @return
     * @throws Exception
     */
    @Override
    public String generateOne(IgeAttribute igeAttribute) throws Exception {
        IncrementStatically incrementStatically = queryConfig(igeAttribute);
        if(incrementStatically==null){
            return Result.error("??????????????????????????????????????????").json();
        }
        if(incrementStatically.getStatus()==IgeConstant.STATUS_UTE_2){
            return Result.error("???????????????????????????????????????").json();
        }
        ServerConfig serverConfig =
                incrementStatically.getOutType().intValue()== IgeConstant.INCREMENTSTATICALLY_OUTTYPE_RETURN?
                        null : serverConfigMapper.selectOne(Wrappers.<ServerConfig>lambdaQuery().eq(ServerConfig::getId, incrementStatically.getStorageType()));
        //????????????
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
        return Result.error("????????????????????????????????????").json();
    }

    /**
     * ????????????
     * @param igeAttributes
     * @return
     */
    @Override
    public String generateBatch(List<IgeAttribute> igeAttributes) throws Exception {
        if(igeAttributes!=null && igeAttributes.size()>0){
            //??????JSONObject????????????
            //igeAttributes = JSON.parseArray(igeAttributes.toString(),IgeAttribute.class);
            IgeAttribute igeAttr = igeAttributes.get(0);
            IncrementStatically incrementStatically = queryConfig(igeAttr);
            if(incrementStatically==null){
                return Result.error("??????????????????????????????????????????").json();
            }
            if(incrementStatically.getStatus()==IgeConstant.STATUS_UTE_2){
                return Result.error("???????????????????????????????????????").json();
            }

            //????????????
            ServerConfig serverConfig =
                    incrementStatically.getOutType().intValue()== IgeConstant.INCREMENTSTATICALLY_OUTTYPE_RETURN?
                    null : serverConfigMapper.selectOne(Wrappers.<ServerConfig>lambdaQuery().eq(ServerConfig::getId, incrementStatically.getStorageType()));
            return process(igeAttributes,serverConfig,incrementStatically.getTemplate(),incrementStatically.getOutType(),incrementStatically.getStorageType());
        }
        return Result.error("????????????????????????????????????").json();
    }

    /**
     * ??????????????????
     * @param igeAttr
     * @return
     */
    private IncrementStatically queryConfig(IgeAttribute igeAttr) {
        return incrementStaticallyMapper.selectOne(Wrappers.<IncrementStatically>lambdaQuery()
                .eq(IncrementStatically::getUniqueId,igeAttr.getUniqueId())
                .eq(IncrementStatically::getNamespaceName,igeAttr.getNamespace()));
    }

    /**
     * ??????????????????
     * @param igeAttr
     * @return
     */
    private FullStatically fullQueryConfig(IgeAttribute igeAttr) {
        return fullStaticallyMapper.selectOne(Wrappers.<FullStatically>lambdaQuery()
                .eq(FullStatically::getUniqueId,igeAttr.getUniqueId())
                .eq(FullStatically::getNamespaceName,igeAttr.getNamespace()));
    }

    /***
     * ??????????????????
     * @param igeAttributes
     * @param serverConfig
     * @param template
     * @param outType
     * @param storageType
     * @return
     * @throws Exception
     */
    public String process(List<IgeAttribute> igeAttributes,ServerConfig serverConfig,String template,int outType,String storageType) throws Exception {
        //3)????????????
        Map<String,String> resultMap = new HashMap<String,String>();
        for (IgeAttribute igeAttribute : igeAttributes) {

            //????????????
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
        //5)??????
        return Result.ok(resultMap).json();
    }

    /***
     * ????????????
     */
    public String generate(IgeAttribute igeAttribute,String template,int outType,String storageType,ServerConfig serverConfig) throws Exception {
        //1)???????????????
        String content = templateProcessor.generate(template,igeAttribute.getData());

        //2)???????????????????????????????????????
        if(outType== IgeConstant.INCREMENTSTATICALLY_OUTTYPE_RETURN){
            return content;
        }

        //3)??????FastDFS???????????????url
        if(outType==IgeConstant.INCREMENTSTATICALLY_OUTTYPE_FASTDFS){
            //??????FastDFS
            FileData fileData = FileData.builder()
                    .id(storageType)
                    .ext(igeAttribute.getExt())
                    .bytes(content.getBytes(Charset.forName(IgeConstant.ENCODING_UTF8)))
                    .metaData(JSON.parseObject(serverConfig.getConfigContent(),Map.class))
                    .build();
            //?????????FastDFS
            RespData respData = fastDFSFileHandler.upload(fileData);
            return respData.getUrl();
        }

        //4)??????AliyunOSS??????
        if(outType==IgeConstant.INCREMENTSTATICALLY_OUTTYPE_ALIYUNOSS){
            //??????AliyunOSS
            FileData fileData = FileData.builder()
                    .ext(igeAttribute.getExt())
                    .fileName(igeAttribute.getFileName())
                    .bytes(content.getBytes())
                    .metaData(JSON.parseObject(serverConfig.getConfigContent(),Map.class))
                    .build();
            //?????????AliyunOSS
            RespData respData = aliyunOssFileHandler.upload(fileData);
            return respData.getUrl();
        }

        //5)??????MinIO??????
        if(outType==IgeConstant.INCREMENTSTATICALLY_OUTTYPE_MINIO){
            //??????MinIO
            FileData fileData = FileData.builder()
                    .id(igeAttribute.getId())
                    .ext(igeAttribute.getExt())
                    .fileName(igeAttribute.getFileName())
                    .bytes(content.getBytes())
                    .metaData(JSON.parseObject(serverConfig.getConfigContent(),Map.class))
                    .build();
            //?????????MinIO
            RespData respData = minIoFileHandler.upload(fileData);
            return respData.getUrl();
        }

        //????????????????????????????????????
        return null;
    }
}
