package com.itheima.storage.fastdfs;

import com.itheima.ige.util.IgeConstant;
import com.itheima.storage.data.FileData;
import com.itheima.storage.data.RespData;
import com.itheima.storage.data.RespFileData;
import com.itheima.storage.fastdfs.handler.ClientGlobal;
import com.itheima.storage.fastdfs.handler.StorageClient;
import com.itheima.storage.fastdfs.handler.TrackerClient;
import com.itheima.storage.fastdfs.handler.TrackerServer;
import com.itheima.storage.fastdfs.util.NameValuePair;
import com.itheima.storage.service.FileHandler;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component(value = "fastDFSFileHandler")
public class FastDFSFileHandler implements FileHandler {


    @Override
    public RespData upload(FileData fileData) throws Exception {
        //获取服务配置
        ClientGlobal clientGlobal = getConf(fileData);
        //获取TrackerClient
        TrackerClient trackerClient = new TrackerClient(clientGlobal);
        //获取TrackerServer
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        //创建StorageClient
        StorageClient storageClient = new StorageClient(trackerServer);
        //实现文件上传
        String[] strings = storageClient.upload_file(fileData.getBytes(), fileData.getExt(), map2Meta(fileData.getMetaData()), clientGlobal);
        //返回数据
        return RespData.builder().url(fileData.getMetaData().get(IgeConstant.STORAGE_FASTDFS_SERVER_HTTP)+strings[0]+"/"+strings[1]).build();
    }

    @Override
    public RespData del(FileData fileData) throws Exception {
        //获取服务配置
        ClientGlobal clientGlobal = getConf(fileData);
        //获取TrackerClient
        TrackerClient trackerClient = new TrackerClient(clientGlobal);
        //获取TrackerServer
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        //创建StorageClient
        StorageClient storageClient = new StorageClient(trackerServer);

        //路径解析
        String[] paths = parseUrl(fileData.getUrl(), fileData.getMetaData().get(IgeConstant.STORAGE_FASTDFS_SERVER_HTTP));
        int count = storageClient.delete_file(paths[0], paths[1], clientGlobal);
        return RespData.builder().url(fileData.getUrl()).code(RespData.SUCCESS_CODE).build();
    }

    //存储所有服务
    private static ConcurrentHashMap<String,ClientGlobal> clientGlobalMap = new ConcurrentHashMap<String,ClientGlobal>();

    /****
     * 初始化服务信息
     */
    public static ClientGlobal getConf(FileData fileData) throws Exception {
        //获取指定服务配置
        ClientGlobal clientGlobal = clientGlobalMap.get(fileData.getId());

        if(clientGlobal==null){
            clientGlobal = new ClientGlobal(fileData.getMetaData());
            //将配置存入
            ClientGlobal cglobal = clientGlobalMap.putIfAbsent(fileData.getId(),clientGlobal);
            return cglobal!=null? cglobal:clientGlobal;
        }
        return clientGlobal;
    }

    public NameValuePair[] map2Meta(Map<String,String> map){
        if(map!=null){
            Set<NameValuePair> metaDataSet = new HashSet<NameValuePair>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                metaDataSet.add(new NameValuePair(entry.getKey(),entry.getValue()));
            }
            return metaDataSet.toArray(new NameValuePair[metaDataSet.size()]);
        }
        return null;
    }

    /***
     * 路径解析
     * @param url
     * @return
     */
    public String[] parseUrl(String url,String domain){
        //去除网址
        String path = url.replace(domain, "");
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
