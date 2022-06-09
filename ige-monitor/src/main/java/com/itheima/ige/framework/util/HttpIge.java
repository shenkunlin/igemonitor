package com.itheima.ige.framework.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @Author：
 * @Description：
 ***/
public class HttpIge {

    /**
     * @description GET封装
     * @return
     */
    public static Result doGetHttp(String url, Map<String,String> headers) throws URISyntaxException {
        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 参数
        URIBuilder builder = new URIBuilder(url);

        //创建HttpGet请求对象
        HttpGet httpGet = new HttpGet(builder.build());

        //请求头
        if (headers != null) {
            for(Map.Entry<String,String>entry:headers.entrySet()){
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 配置信息
            RequestConfig requestConfig = reqConfig();

            // 将上面的配置信息 运用到这个Get请求里
            httpGet.setConfig(requestConfig);
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                return JSON.parseObject(EntityUtils.toString(responseEntity),Result.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Result.error();
    }


    /**
     * @description POST封装
     * @return
     */
    public static Result doPostHttp(Object data,  String url,Map<String,String> headers) {
        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url);
        if(data!=null){
            //入参
            // 利用阿里的fastjson，将Object转换为json字符串;
            // (需要导入com.alibaba.fastjson.JSON包)
            String jsonString = JSON.toJSONString(data);
            StringEntity entity = new StringEntity(jsonString, "UTF-8");
            // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        }

        //请求头
        if (headers != null) {
            for(Map.Entry<String,String>entry:headers.entrySet()){
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            RequestConfig requestConfig = reqConfig();
            httpPost.setConfig(requestConfig);

            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                return JSON.parseObject(EntityUtils.toString(responseEntity),Result.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Result.error();
    }

    private static RequestConfig reqConfig() {
        // 配置信息
        return RequestConfig.custom()
                // 设置连接超时时间(单位毫秒)
                .setConnectTimeout(10000)
                // 设置请求超时时间(单位毫秒)
                .setConnectionRequestTimeout(10000)
                // socket读写超时时间(单位毫秒)
                .setSocketTimeout(10000)
                // 设置是否允许重定向(默认为true)
                .setRedirectsEnabled(true).build();
    }
}
