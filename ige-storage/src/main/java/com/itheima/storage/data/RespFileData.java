package com.itheima.storage.data;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

/***
 * 文件管理公共数据类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RespFileData implements Serializable {

    //状态码
    public static final Integer SUCCESS_CODE=200;
    public static final Integer ERROR_CODE=500;

    /***
     * 文件访问路径
     */
    private String url;

    /***
     * 状态
     */
    private Integer code;

    /***
     * 文件流
     */
    private byte[] bytes;

    /***
     * 文件流
     */
    private InputStream is;

    /***
     * 消息
     */
    private String message;

    /****
     * 其他数据
     */
    private Map<String,Object> data;

    public String json(){
        return JSON.toJSONString(this);
    }
}
