package com.itheima.storage.ige;
import com.itheima.storage.data.FileData;
import com.itheima.storage.data.RespData;
import com.itheima.storage.data.RespFileData;
import com.itheima.storage.service.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static com.aliyun.oss.internal.OSSConstants.DEFAULT_BUFFER_SIZE;

@Component(value = "igeFileHandler")
public class IgeFileHandler implements FileHandler {

    @Autowired
    private IgeStorageConfig igeStorageConfig;

    @Override
    public RespData upload(FileData fileData) throws Exception {
        //构建路径
        String filePath = igeStorageConfig.getPaths().get(fileData.getIgeGroup())+fileData.getFileName();
        File file = new File(filePath);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream os = new FileOutputStream(file);
        while ((index = fileData.getIs().read(bytes)) != -1) {
            os.write(bytes, 0, index);
            os.flush();
        }
        os.close();
        fileData.getIs().close();
        //访问地址
        String url = igeStorageConfig.getUrl()+fileData.getIgeGroup()+"/"+fileData.getFileName();
        return RespData.builder().url(url).code(RespData.SUCCESS_CODE).build();
    }

    @Override
    public RespData del(FileData fileData) throws Exception {
        //去除url获取group
        String[] filePaths = parseUrl(fileData.getUrl());

        //根据group获取路径
        String path = igeStorageConfig.getPaths().get(filePaths[0]);

        //路径+文件路径
        String realPath = path+filePaths[1];
        File file = new File(realPath);
        if(file.exists()){
            file.delete();
        }
        return RespData.builder().url(realPath).code(RespData.SUCCESS_CODE).message("删除成功！").build();
    }

    /***
     * url解析
     */
    public String[] parseUrl(String url){
        //去除网址
        String path = url.replace(igeStorageConfig.getUrl(), "");
        //分割
        String[] split = path.split("/");
        System.out.println(split[0]);

        //获取路径
        StringBuffer buffer = new StringBuffer();
        for (int i = 1; i < split.length; i++) {
            if(i!=1){
                buffer.append("/");
            }
            buffer.append(split[i]);
        }
        return new String[]{split[0],buffer.toString()};
    }
}
