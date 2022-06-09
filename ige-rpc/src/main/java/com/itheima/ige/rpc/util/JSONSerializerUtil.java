package com.itheima.ige.rpc.util;
import com.alibaba.fastjson.JSON;
public class JSONSerializerUtil  {

    //序列化
    public static byte[]serialize(Object object){
        return JSON.toJSONBytes(object);
    }

    //反序列化
    public static <T> T deserialize(byte[] bytes,Class<T> clazz){
        return JSON.parseObject(bytes, clazz);
    }
}
