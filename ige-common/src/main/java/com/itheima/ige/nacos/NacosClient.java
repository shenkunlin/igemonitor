package com.itheima.ige.nacos;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ServiceInfo;
import com.alibaba.nacos.client.utils.ParamUtil;
import com.alibaba.nacos.common.constant.HttpHeaderConsts;
import com.alibaba.nacos.common.http.HttpClientConfig;
import com.alibaba.nacos.common.http.HttpRestResult;
import com.alibaba.nacos.common.http.client.NacosRestTemplate;
import com.alibaba.nacos.common.http.client.request.JdkHttpClientRequest;
import com.alibaba.nacos.common.http.param.Header;
import com.alibaba.nacos.common.http.param.Query;
import com.alibaba.nacos.common.utils.MD5Utils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.alibaba.nacos.common.utils.VersionUtils;
import com.itheima.ige.util.IgeConstant;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author：
 * @Description：https://nacos.io/zh-cn/docs/open-api.html
 ***/
public class NacosClient {

    public static AtomicInteger index = new AtomicInteger(0);

    /**
     * 实例集合查找
     * @throws Exception
     * @return
     */
    public static Instance chooseInstance(Map<String, String> params,String url)throws Exception{
        //创建链接
        NacosRestTemplate template = createTemplate();
        //查询Nacos
        HttpRestResult<String> response=template.get(
                url+ IgeConstant.NACOS_INSTANCE_URL,
                getSpasHeaders("UTF-8"),
                Query.newInstance().initParams(params),String.class);

        if (response.ok()) {
            ServiceInfo serviceInfo = JSON.parseObject(response.getData(),ServiceInfo.class);
            //随机选择一个服务
            return choose(serviceInfo);
        }
        return null;
    }

    /***
     * 负载均衡
     * @return
     */
    private static Instance choose(ServiceInfo serviceInfo){
        List<Instance> hosts = serviceInfo.getHosts();
        if(hosts!=null && hosts.size()>0){
            return hosts.get(index.incrementAndGet() % hosts.size());
        }
        return null;
    }

    /**
     * 请求头封装
     * @param encode
     * @return
     * @throws Exception
     */
    private static Header getSpasHeaders(String encode) throws Exception {
        Header header = Header.newInstance();
        String ts = String.valueOf(System.currentTimeMillis());
        String token = MD5Utils.md5Hex(ts + ParamUtil.getAppKey(), Constants.ENCODE);
        header.addParam(Constants.CLIENT_APPNAME_HEADER, ParamUtil.getAppName());
        header.addParam(Constants.CLIENT_REQUEST_TS_HEADER, ts);
        header.addParam(Constants.CLIENT_REQUEST_TOKEN_HEADER, token);
        header.addParam(HttpHeaderConsts.CLIENT_VERSION_HEADER, VersionUtils.version);
        header.addParam("exConfigInfo", "true");
        header.addParam(HttpHeaderConsts.REQUEST_ID, UuidUtils.generateUuid());
        header.addParam(HttpHeaderConsts.ACCEPT_CHARSET, encode);
        return header;
    }

    /**
     * 初始化链接对象
     * @return
     */
    private static NacosRestTemplate createTemplate() {
        //超时时间
        HttpClientConfig httpClientConfig= HttpClientConfig.builder().setConTimeOutMillis(10000).setReadTimeOutMillis(10000 >> 1).build();
        //JdkHttpClientRequest内部创建与服务端的连接
        JdkHttpClientRequest request=new JdkHttpClientRequest(httpClientConfig);
        //模板类，对URL地址和参数做处理，并调用JdkHttpClientRequest
        return new NacosRestTemplate(LoggerFactory.getLogger("root"),request);
    }

    /**
     * 服务配置
     */
    public static Map<String,String> param(String group,String namespace,String serverName){
        //封装参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("groupName", group);
        params.put("namespaceId", namespace);
        params.put("serviceName", serverName);
        return params;
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> param = NacosClient.param(
                "DEFAULT_GROUP",  //分组
                "seckill_dev",  //命名空间ID
                "seckill-goods"); //服务名字
        String host ="http://192.168.211.130:8848";
        Instance instance = chooseInstance(param, host);
        System.out.println(instance.getIp());
        System.out.println(instance.getPort());
        System.out.println(instance);
    }
}
