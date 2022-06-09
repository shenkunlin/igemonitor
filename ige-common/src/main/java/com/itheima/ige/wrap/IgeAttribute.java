package com.itheima.ige.wrap;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.DigestUtils;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * @Author：
 * @date： 2022/3/14 7:29
 * @Description： 静态页生成属性封装对象
 ***/
@Data
@NoArgsConstructor
public class IgeAttribute {

    //唯一编号，不允许为空
    private String id;

    //数据模型
    private Map<String,Object> data;

    //业务编号
    private String uniqueId;

    //文件名字  远程本地保存使用
    private String fileName;

    //文件名字  远程本地保存使用
    private String ext;

    //签名
    private String signature;

    //命名空间
    private String namespace;

    public IgeAttribute data(Object data){
        //防止有JavaBean，将JavaBean转成Map
        this.data=JSON.parseObject(JSON.toJSONString(data),Map.class);
        return this;
    }

    public static IgeAttribute build(String id, String namespace,String uniqueId, String ext,String fileName){
        IgeAttribute igeAttribute = new IgeAttribute(id, namespace, uniqueId, ext, fileName);
        return igeAttribute;
    }

    public IgeAttribute(String id, String namespace,String uniqueId, String ext) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.ext = ext;
        this.namespace = namespace;
    }

    public IgeAttribute(String id, String namespace,String uniqueId, String ext,String fileName) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.ext = ext;
        this.namespace = namespace;
        this.fileName = fileName;
    }

    public IgeAttribute(String id, String namespace,String uniqueId, String ext,Object data) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.ext = ext;
        this.namespace = namespace;
        this.data=JSON.parseObject(JSON.toJSONString(data),Map.class);
    }

    public IgeAttribute(String id, String namespace,String uniqueId, String ext,String fileName,Object data) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.ext = ext;
        this.namespace = namespace;
        this.fileName = fileName;
        this.data=JSON.parseObject(JSON.toJSONString(data),Map.class);
    }

    public IgeAttribute(Object data, String uniqueId, String fileName, String namespace, String secret) {
        this.data=JSON.parseObject(JSON.toJSONString(data),Map.class);
        this.uniqueId = uniqueId;
        this.fileName = fileName;
        this.namespace=namespace;

        //签名生成
        String params= JSON.toJSONString(this);
        this.signature =
                DigestUtils.md5DigestAsHex((secret+params+secret).getBytes(Charset.forName("UTF-8")));
    }

    public IgeAttribute(Object data, String namespace, String uniqueId) {
        this.data=JSON.parseObject(JSON.toJSONString(data),Map.class);
        this.uniqueId = uniqueId;
        this.namespace=namespace;
    }

    /**
     * 签名校验
     */
    public static Boolean signature(IgeAttribute igeAttribute,String secret){
        //获取签名
        String signature = igeAttribute.getSignature();
        //清空签名
        igeAttribute.setSignature(null);
        //生成新签名
        String params= JSON.toJSONString(igeAttribute);
        String newSignature=DigestUtils.md5DigestAsHex((secret+params+secret).getBytes(Charset.forName("UTF-8")));
        //签名校验结果
        return signature.equals(newSignature);
    }
}
