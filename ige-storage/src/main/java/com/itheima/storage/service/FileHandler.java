package com.itheima.storage.service;

import com.itheima.storage.data.FileData;
import com.itheima.storage.data.RespData;
import com.itheima.storage.data.RespFileData;

/****
 * 文件处理公共接口
 */
public interface FileHandler {

    /****
     * 文件上传
     */
    RespData upload(FileData fileData) throws Exception;


    /***
     * 文件删除
     */
    RespData del(FileData fileData) throws Exception;
}