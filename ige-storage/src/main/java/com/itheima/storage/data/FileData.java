package com.itheima.storage.data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileData implements Serializable {

    /***
     * 唯一识别码
     */
    private String id;

    /***
     * 文件字节数组
     */
    private byte[] bytes;

    /***
     * 文件输入流
     */
    private InputStream is;

    /***
     * 后缀  jpg,html
     */
    private String ext;

    /***
     * 文件大小
     */
    private long size;

    /***
     * 明明规则
     */
    private Integer nameRule;

    /***
     * 文件名字
     */
    private String fileName;

    /***
     * 路径
     */
    private String path;

    /***
     * 访问路径
     */
    private String url;

    /***
     * ige分组
     */
    private String igeGroup;

    /***
     * 其他数据
     */
    private Map<String,String> metaData;

    public static String datePath(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date());
    }
}
