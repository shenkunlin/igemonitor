package com.itheima.ige.monitor.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.ige.framework.util.Result;
import com.itheima.ige.monitor.mapper.IncrementStaticallyMapper;
import com.itheima.ige.monitor.mapper.ServerConfigMapper;
import com.itheima.ige.monitor.pojo.IncrementStatically;
import com.itheima.ige.monitor.pojo.ServerConfig;
import com.itheima.ige.monitor.service.IncrementStaticallyService;
import com.itheima.ige.template.TemplateProcessor;
import com.itheima.ige.util.IgeConstant;
import com.itheima.ige.wrap.IgeAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 增量静态化配置 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-03-14
 */
@Service
public class IncrementStaticallyServiceImpl extends ServiceImpl<IncrementStaticallyMapper, IncrementStatically> implements IncrementStaticallyService {

    @Autowired
    @Qualifier("freemarkerProcessor")
    private TemplateProcessor templateProcessor;

    @Autowired
    private IncrementStaticallyMapper incrementStaticallyMapper;

    @Autowired
    private ServerConfigMapper serverConfigMapper;

    /**
     * 批量操作
     * @param igeAttributes
     * @return
     */
    @Override
    public Result initiativeGenerateBatch(List<IgeAttribute> igeAttributes) throws Exception {
        if(igeAttributes!=null && igeAttributes.size()>0){
            IgeAttribute igeAttr = igeAttributes.get(0);
            //1)签名校验
            Result vierfyResult = verifySignature(igeAttr);
            if (vierfyResult != null){
                return vierfyResult;
            };

            IncrementStatically incrementStatically = queryConfig(igeAttr);
            if(incrementStatically==null){
                return Result.error("不存在对应的增量静态化配置！");
            }
            if(incrementStatically.getStatus()==IgeConstant.STATUS_UTE_2){
                return Result.error("请先开启该增量静态化功能！");
            }

            //2)批量生成
            List<String> contents = new ArrayList<String>();
            for (IgeAttribute igeAttribute : igeAttributes) {
                //3)执行生成操作-本地输出
                if(incrementStatically.getOutType().intValue()== IgeConstant.INCREMENTSTATICALLY_OUTTYPE_LOCAL){
                    localGen(incrementStatically, igeAttribute);
                }
                //4)如果需要返回，则返回生成内容
                if(incrementStatically.getOutType().intValue()== IgeConstant.INCREMENTSTATICALLY_OUTTYPE_RETURN){
                    String content = templateProcessor.generate(incrementStatically.getTemplate(),igeAttribute.getData());
                    contents.add(content);
                }
            }
            //5)返回
            return incrementStatically.getOutType().intValue()== IgeConstant.INCREMENTSTATICALLY_OUTTYPE_RETURN? Result.ok(contents):Result.ok();
        }
        return Result.error("静态页生成未能正确执行！");
    }

    /**
     * 客户端主动生成文件
     * @param igeAttribute
     * @return
     */
    @Override
    public Result initiativeGenerate(IgeAttribute igeAttribute) throws Exception {
        //1)签名校验
        Result vierfyResult = verifySignature(igeAttribute);
        if (vierfyResult != null){
            return vierfyResult;
        };

        //2)根据命名空间和唯一业务表示查询增量备份数据
        IncrementStatically incrementStatically = queryConfig(igeAttribute);
        if(incrementStatically==null){
            return Result.error("不存在对应的增量静态化配置！");
        }
        if(incrementStatically.getStatus()==IgeConstant.STATUS_UTE_2){
            return Result.error("请先开启该增量静态化功能！");
        }

        //3)执行生成操作-本地输出
        if(incrementStatically.getOutType().intValue()== IgeConstant.INCREMENTSTATICALLY_OUTTYPE_LOCAL){
            localGen(incrementStatically, igeAttribute);
            return Result.ok();
        }
        //4)如果需要返回，则返回生成内容
        if(incrementStatically.getOutType().intValue()== IgeConstant.INCREMENTSTATICALLY_OUTTYPE_RETURN){
            String content = templateProcessor.generate(incrementStatically.getTemplate(),igeAttribute.getData());
            return Result.ok(content);
        }
        return Result.error("静态页生成未能正确执行！");
    }

    /**
     * 本地生成
     * @param incrementStatically
     * @param igeAttribute
     * @throws Exception
     */
    private void localGen(IncrementStatically incrementStatically, IgeAttribute igeAttribute) throws Exception {
        templateProcessor.generateDir(
                incrementStatically.getTemplate(),   //模板
                igeAttribute.getData(), //数据模型
                igeAttribute.getFileName(),             //文件名字
                incrementStatically.getLocalPath());    //存储路径
    }

    /**
     * 增量配置查询
     * @param igeAttr
     * @return
     */
    private IncrementStatically queryConfig(IgeAttribute igeAttr) {
        QueryWrapper<IncrementStatically> wrapper = new QueryWrapper<IncrementStatically>();
        wrapper.eq("unique_id", igeAttr.getUniqueId());
        wrapper.eq("namespace_name", igeAttr.getNamespace());
        return incrementStaticallyMapper.selectOne(wrapper);
    }

    /**
     * 签名校验
     * @param igeAttribute
     * @return
     */
    private Result verifySignature(IgeAttribute igeAttribute) {
        //1)命名空间配置检查
        QueryWrapper<ServerConfig> serverConfigQueryWrapper = new QueryWrapper<ServerConfig>();
        serverConfigQueryWrapper.eq("namespace_name",igeAttribute.getNamespace());
        serverConfigQueryWrapper.eq("enable", IgeConstant.ENABLE_EN_1);
        ServerConfig serverConfig = serverConfigMapper.selectOne(serverConfigQueryWrapper);
        if(serverConfig==null){
            return Result.error("为找到有效的该服务！");
        }
        //2)验签校验
        if(serverConfig.getServerType()==IgeConstant.SERVER_HTTP_2){
            //Http模式,验签校验
            Map<String,String> configMap = JSON.parseObject(serverConfig.getConfigContent(),Map.class);
            if(!IgeAttribute.signature(igeAttribute,configMap.get(IgeConstant.SERVER_HTTP_SECRET))){
                return Result.error("请提供正确的签名！");
            }
        }
        return null;
    }

    /**
     * 根据唯一业务ID获取服务数据
     * @param uniqueId
     * @return
     */
    @Override
    public ServerConfig getByUniqueId(String uniqueId) {
        //1)查询增量配置

        //2)根据命名空间查询服务配置

        return null;
    }


}
