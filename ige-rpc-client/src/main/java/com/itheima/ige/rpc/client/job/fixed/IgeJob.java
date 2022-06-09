package com.itheima.ige.rpc.client.job.fixed;

import com.itheima.ige.wrap.IgeAttribute;
import com.itheima.ige.wrap.IgeStorage;

import java.util.List;

public interface IgeJob {

    /****
     * 查询总数
     */
    default long total(){
        return 0;
    }

    /***
     * 分页查询
     */
    List<IgeAttribute> list(IgePage igePage);

    /****
     * 返回方法
     */
    default void callback(String result){}
}
