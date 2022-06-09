package com.itheima.ige.util;

/**
 * @Author：
 * @Description：常量
 ***/
public class IgeConstant {

    //1：启用生效   2：停用弃用
    public static final int STATUS_TE_1=1;
    public static final int STATUS_UTE_2=2;

    //1：启用   2：停用
    public static final int ENABLE_EN_1=1;
    public static final int ENABLE_UN_2=2;

    //原路返回
    public static final int INCREMENTSTATICALLY_OUTTYPE_RETURN=0;

    //取消本地存储模式
    public static final int INCREMENTSTATICALLY_OUTTYPE_LOCAL=-1;

    //FastDFS
    public static final int INCREMENTSTATICALLY_OUTTYPE_FASTDFS=1;

    //AliyunOSS
    public static final int INCREMENTSTATICALLY_OUTTYPE_ALIYUNOSS=2;

    //MinIO
    public static final int INCREMENTSTATICALLY_OUTTYPE_MINIO=3;

    //本地输出
    public static final int FULLSTATICALLY_OUTTYPE_LOCAL=1;
    //原路返回
    public static final int FULLSTATICALLY_OUTTYPE_RETURN=2;


    //服务配置类型  1:Nacos        2:Http
    public static final int SERVER_NACOS_1=1;
    public static final int SERVER_HTTP_2=2;

    //服务类型中的Key
    public static final String SERVER_HTTP_ADDRESS="address";
    public static final String SERVER_HTTP_SECRET="secret";
    public static final String SERVER_NACOS_IP="ip";
    public static final String SERVER_NACOS_PORT="port";
    public static final String SERVER_NACOS_GROUP="group";

    //全量类型  Full：批量同步   Page：分页批量同步
    public static final String FULLSTATIC_FULL="Full";
    public static final String FULLSTATIC_PAGE="Page";

    //Nacos配置
    public static final String NACOS_INSTANCE_URL="/nacos/v1/ns/instance/list";  //服务列表查询

    //Http协议
    public static final String HTTP="http://";
    //分隔符
    public static final String SPLIT = "[|]";

    //FASTDFS存储服务
    public static final String STORAGE_FASTDFS_SERVER_TRACKER = "tracker_server";
    public static final String STORAGE_FASTDFS_SERVER_HTTP = "server_http";

    //ALITUNOSS
    public static final String STORAGE_ALIYUNOSS_ENDPOINT = "endpoint";
    public static final String STORAGE_ALIYUNOSS_ENDPOINT_DEFAULT = "https://oss-cn-hangzhou.aliyuncs.com";
    public static final String STORAGE_ALIYUNOSS_ACCESS_KEY = "access-key";
    public static final String STORAGE_ALIYUNOSS_ACCESS_SECRET = "access-secret";
    public static final String STORAGE_ALIYUNOSS_BUCKET_NAME = "bucket-name";
    public static final String STORAGE_ALIYUNOSS_CONTENT_TYPE = "content-type";
    public static final String STORAGE_ALIYUNOSS_URL = "url";
    public static final String STORAGE_ALIYUNOSS_URL_ENDPOINT = "endpoint";


    //编码
    public static final String ENCODING_UTF8="UTF-8";

    //保存
    public static final String ZERO ="0";
    public static final int OUTTYPE_RETURN =0;
    public static final int OUTTYPE_FASTDFS =1;
    public static final int OUTTYPE_ALIYUNOSS =2;
    public static final int OUTTYPE_MINIO =3;

    //Ige定时任务参数
    public static final String FULLJOB_PREFIX="ige.client.jobs.";
    public static final String NAMESPACE="namespace";
    public static final String UNIQUEID="uniqueid";
    public static final String BEANNAME="bean";
}
