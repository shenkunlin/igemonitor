package com.itheima.storage.minio;

import com.itheima.storage.data.FileData;
import com.itheima.storage.data.RespData;
import com.itheima.storage.service.FileHandler;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component(value = "minIoFileHandler")
public class MinIoFileHandler implements FileHandler {

    //分隔符
    public final static String SEPARATOR="/";

    /***
     * 如果把所有url写了，则自动处理
     * @param fileData
     * @return
     */
    public String fileNameParse(FileData fileData){
        //服务地址
        String endpoint = fileData.getMetaData().get(MinConstant.ENDPOINT);
        String bucket = fileData.getMetaData().get(MinConstant.BUCKET);
        String prefix=endpoint+SEPARATOR+bucket+SEPARATOR;
        String fileName = fileData.getFileName();
        if(fileName.startsWith(prefix)){
            fileName=fileName.replaceFirst(prefix,"");
        }
        return fileName;
    }

    @Override
    public RespData upload(FileData fileData) throws Exception {
        //文件名字
        String fileName = ObjectUtils.isEmpty(fileData.getFileName())? autoBuildFile(fileData.getExt()) : fileNameParse(fileData);
        try {
            //获取客户端
            MinioClient minioClient = client(fileData);
            InputStream is = new ByteArrayInputStream(fileData.getBytes());
            Map<String, String> metaData = fileData.getMetaData();
            String contentType = !ObjectUtils.isEmpty(metaData.get(MinConstant.FILE_TYPE))? metaData.get(MinConstant.FILE_TYPE).toString() :MinConstant.FILE_TYPE_DEFUALT;
            //文件上传
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(fileName)
                    .contentType(contentType)
                    .bucket(metaData.get(MinConstant.BUCKET)).stream(is,is.available(),-1)
                    .build();
            minioClient.putObject(putObjectArgs);

            StringBuilder urlPath = new StringBuilder(metaData.get(MinConstant.ENDPOINT)+SEPARATOR+metaData.get(MinConstant.BUCKET));
            urlPath.append(SEPARATOR+fileName);
            return RespData.builder().url(urlPath.toString()).code(RespData.SUCCESS_CODE).build();
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("上传文件失败");
        }
    }

    /***
     * 文件删除
     * @param fileData
     * @return
     * @throws Exception
     */
    @Override
    public RespData del(FileData fileData) throws Exception {
        MinioClient minioClient = client(fileData);
        Map<String, String> metaData = fileData.getMetaData();
        String key = fileData.getUrl().replace(metaData.get(MinConstant.ENDPOINT)+SEPARATOR,"");
        int index = key.indexOf(SEPARATOR);
        String bucket = key.substring(0,index);
        String filePath = key.substring(index+1);
        // 删除Objects
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucket).object(filePath).build();
        minioClient.removeObject(removeObjectArgs);
        return RespData.builder().url(fileData.getPath()).code(RespData.SUCCESS_CODE).build();
    }

    /**
     * 自动构建文件目录
     * @return
     */
    public String autoBuildFile(String ext) {
        StringBuilder stringBuilder = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String todayStr = sdf.format(new Date());
        stringBuilder.append(todayStr).append(SEPARATOR);
        stringBuilder.append(UUID.randomUUID().toString()+"."+ext);
        return stringBuilder.toString();
    }

    //存储所有服务
    private static ConcurrentHashMap<String, MinioClient> clientGlobalMap = new ConcurrentHashMap<String,MinioClient>();

    public MinioClient client(FileData fileData) {
        //获取指定服务配置
        MinioClient minioClient = clientGlobalMap.get(fileData.getId());

        if(minioClient==null){
            Map<String,String> metaData = fileData.getMetaData();

            minioClient=MinioClient
                    .builder().credentials(
                            metaData.get(MinConstant.ACCESS_KEY),
                            metaData.get(MinConstant.SECRET_KEY))
                    .endpoint(metaData.get(MinConstant.ENDPOINT))
                    .build();
            //将配置存入
            MinioClient cglobal = clientGlobalMap.putIfAbsent(fileData.getId(),minioClient);
            return cglobal!=null? cglobal:minioClient;
        }
        return minioClient;
    }

}
