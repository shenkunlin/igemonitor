package com.itheima.storage.alioss;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.itheima.ige.util.IgeConstant;
import com.itheima.storage.data.FileData;
import com.itheima.storage.data.RespData;
import com.itheima.storage.data.RespFileData;
import com.itheima.storage.service.FileHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.util.Map;
import java.util.UUID;
import java.util.zip.CheckedInputStream;

import static com.aliyun.oss.internal.OSSConstants.DEFAULT_BUFFER_SIZE;

@Slf4j
@Component(value = "aliyunOssFileHandler")
public class AliyunOssFileHandler implements FileHandler {

    @Autowired
    private AliyunConfig aliyunConfig;

    //增删下载
    public final int UPLOAD=1,DOWNLOAD=2,DEL=3;

    @Override
    public RespData upload(FileData fileData) throws Exception {
        return fileHandler(fileData,UPLOAD);
    }

    @Override
    public RespData del(FileData fileData) throws Exception {
        return fileHandler(fileData,DEL);
    }

    /***
     * 文件操作
     * @param type
     * @return
     */
    public <T> T fileHandler(FileData fileData,int type){
        OSS ossClient = getOss(fileData.getMetaData());
        try {
            switch (type){
                case UPLOAD:
                    InputStream is = new ByteArrayInputStream(fileData.getBytes());
                    // 创建PutObject请求。
                    ObjectMetadata metadata = new ObjectMetadata();
                    String contentType=fileData.getMetaData().get(IgeConstant.STORAGE_ALIYUNOSS_CONTENT_TYPE);
                    String bucket_name = fileData.getMetaData().get(IgeConstant.STORAGE_ALIYUNOSS_BUCKET_NAME);
                    metadata.setContentType(contentType);
                    //自定义文件名字
                    String fileName= ObjectUtils.isEmpty(fileData.getFileName())? FileData.datePath()+"/"+ UUID.randomUUID().toString()+"."+fileData.getExt():fileData.getFileName();
                    ossClient.putObject(bucket_name,
                            fileName,
                            is,
                            metadata);
                    return (T) RespData.builder().url(fileData.getMetaData().get(IgeConstant.STORAGE_ALIYUNOSS_URL) + fileName).code(RespData.SUCCESS_CODE).build();
                case DEL:
                    // 删除文件或目录。如果要删除目录，目录必须为空。
                    String bucketname = fileData.getMetaData().get(IgeConstant.STORAGE_ALIYUNOSS_BUCKET_NAME);
                    String domian = fileData.getMetaData().get(IgeConstant.STORAGE_ALIYUNOSS_URL);
                    ossClient.deleteObject(bucketname, fileData.getUrl().replace(domian, ""));
                    return (T) RespData.builder().url(fileData.getUrl()).code(RespData.SUCCESS_CODE).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("IO error", e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }

    /***
     * 获取OSS对象
     * @return
     */
    private OSS getOss(Map<String,String> map) {
        String key =map.get(IgeConstant.STORAGE_ALIYUNOSS_ACCESS_KEY);
        String secret =map.get(IgeConstant.STORAGE_ALIYUNOSS_ACCESS_SECRET);
        // 创建OSSClient实例。
        return new OSSClientBuilder()
                .build(map.getOrDefault(IgeConstant.STORAGE_ALIYUNOSS_ENDPOINT,IgeConstant.STORAGE_ALIYUNOSS_ENDPOINT_DEFAULT),
                        key,
                        secret);
    }
}
