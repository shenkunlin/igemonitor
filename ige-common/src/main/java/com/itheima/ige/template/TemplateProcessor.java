package com.itheima.ige.template;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * @Author：
 * @date： 2022/3/13 22:55
 * @Description：模板文件处理
 ***/
public interface TemplateProcessor {

    /**
     * 传入模板和数据模型，并返回生成的内容
     * @param template : 模板
     * @param data : 数据模型
     * @return 生成的内容
     */
    String generate(String template, Object data) throws IOException, TemplateException, Exception;

    /**
     * 传入模板名称和数据模型，并返回生成的内容
     * @param templateName : 模板名称
     * @param data : 数据模型
     * @return 生成的内容
     */
    String loadTemplateGenerate(String templateName, Object data) throws IOException, Exception;

    /**
     * 传入模板名称和数据模型，并生成内容写入配置目录
     * @param templateName : 模板名称
     * @param data : 数据模型
     * @param fileName : 生成的文件名字
     */
    void loadTemplateGenerateDir(String templateName, Object data, String fileName) throws IOException, Exception;

    /**
     * 传入模板名称和数据模型，并生成内容写入指定目录
     * @param templateName : 模板名称
     * @param data : 数据模型
     * @param path : 存储文件路径
     * @param fileName : 生成的文件名字
     */
    void loadTemplateGenerateDir(String templateName, Object data, String path, String fileName) throws IOException, Exception;

    /**
     * 传入模板和数据模型，将文件生成存入到dir目录
     * @param template : 模板
     * @param data : 数据模型
     * @param fileName : 生成的文件名字
     */
    void generateDir(String template, Object data, String fileName) throws IOException, Exception;

    /**
     * 传入模板和数据模型，将文件生成存入到dir目录
     * @param template : 模板
     * @param data : 数据模型
     * @param fileName : 生成的文件名字
     * @param path : 存储路劲
     */
    void generateDir(String template, Object data, String fileName, String path) throws IOException, Exception;

    void delFile(String path, String fileName);

    /**
     * 删除指定目录下的文件
     * @param fileName : 生成的文件名字
     */
    void delDirFile(String fileName);
}
