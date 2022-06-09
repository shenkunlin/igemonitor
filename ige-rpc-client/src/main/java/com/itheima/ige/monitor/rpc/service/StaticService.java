package com.itheima.ige.monitor.rpc.service;

import com.itheima.ige.rpc.annotation.Client;
import com.itheima.ige.wrap.IgeAttribute;

import java.util.List;

@Client
public interface StaticService {

    String test(String name);

    /***
     * 根据服务名字获取所有全量任务
     */
    String loadAllFullJobs(String namespace);

    String fullGenerateBatch(List<IgeAttribute> igeAttributes) throws Exception;

    /***
     * 生成1个页面
     * @return
     * @throws Exception
     */
    String generateOne(IgeAttribute igeAttribute) throws Exception;

    /***
     *
     * @param igeAttributes
     * @return
     * @throws Exception
     */
    String generateBatch(List<IgeAttribute> igeAttributes) throws Exception;

    /***
     * 删除文件
     */
    String del(String url) throws Exception;
}