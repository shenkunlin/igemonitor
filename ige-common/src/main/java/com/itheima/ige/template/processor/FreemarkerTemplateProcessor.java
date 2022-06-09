package com.itheima.ige.template.processor;

import com.itheima.ige.template.TemplateProcessor;
import com.itheima.ige.template.config.TemplateConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

/**
 * @Author：
 * @date： 2022/3/13 22:54
 * @Description：Freemarker实现模板生成
 *  1:@ConditionalOnClass(Configuration.class){程序必须启用了Freemarker才生效}
 *  2:@EnableConfigurationProperties(TemplateConfig.class){让TemplateConfig配置类生效，并注入SpringIOC容器中}
 ***/
@Component(value = "freemarkerProcessor")
@ConditionalOnClass(Configuration.class)
@EnableConfigurationProperties(TemplateConfig.class)
public class FreemarkerTemplateProcessor implements TemplateProcessor {

    @Autowired
    private Configuration configuration;

    @Autowired
    private TemplateConfig templateConfig;

    /**
     * 传入模板和数据模型，并返回生成的内容
     * @param template : 模板
     * @param data : 数据模型
     * @return 生成的内容
     */
    @Override
    public String generate(String template, Object data) throws Exception{
        return StringUtils.isEmpty(template)? null:parseProcess(template,data);
    }

    /**
     * 传入模板名称和数据模型，并返回生成的内容
     * @param templateName : 模板名称
     * @param data : 数据模型
     * @return 生成的内容
     */
    @Override
    public String loadTemplateGenerate(String templateName, Object data) throws Exception {
        if(StringUtils.isEmpty(templateName)){
            return null;
        }
        return process(configuration.getTemplate(templateName),data);
    }

    /**
     * 传入模板名称和数据模型，并生成内容写入配置目录
     * @param templateName : 模板名称
     * @param data : 数据模型
     * @param fileName : 生成的文件名字
     */
    @Override
    public void loadTemplateGenerateDir(String templateName, Object data, String fileName) throws Exception {
        if(StringUtils.isEmpty(templateName) || StringUtils.isEmpty(fileName)){
            return;
        }
        processDir(configuration.getTemplate(templateName),data,templateConfig.getDir(),fileName);
    }

    /**
     * 传入模板名称和数据模型，并生成内容写入指定目录
     * @param templateName : 模板名称
     * @param data : 数据模型
     * @param path : 存储文件路径
     * @param fileName : 生成的文件名字
     */
    @Override
    public void loadTemplateGenerateDir(String templateName, Object data, String path, String fileName) throws Exception {
        if(StringUtils.isEmpty(templateName) || StringUtils.isEmpty(fileName)){
            return;
        }
        processDir(configuration.getTemplate(templateName),data,path,fileName);
    }

    /**
     * 传入模板和数据模型，将文件生成存入到dir目录
     * @param template : 模板
     * @param data : 数据模型
     * @param fileName : 生成的文件名字
     */
    @Override
    public void generateDir(String template, Object data, String fileName) throws Exception {
        if(StringUtils.isEmpty(template) || StringUtils.isEmpty(fileName)){
            return;
        }
        processDir(new Template(null, new StringReader(template), configuration),data,templateConfig.getDir(),fileName);
    }

    /**
     * 传入模板和数据模型，将文件生成存入到dir目录
     * @param template : 模板
     * @param data : 数据模型
     * @param fileName : 生成的文件名字
     * @param path : 存储路径
     */
    @Override
    public void generateDir(String template, Object data, String fileName, String path) throws IOException, Exception {
        if(StringUtils.isEmpty(template) || StringUtils.isEmpty(fileName)){
            return;
        }
        processDir(new Template(null, new StringReader(template), configuration),data,path,fileName);
    }

    /**
     * 删除指定目录下的文件
     * @param fileName : 文件名字
     */
    @Override
    public void delFile(String path, String fileName) {
        File file = new File(path, fileName);
        file.delete();
    }

    /**
     * 删除指定目录下的文件
     * @param fileName : 生成的文件名字
     */
    @Override
    public void delDirFile(String fileName) {
        File file = new File(templateConfig.getDir(), fileName);
        file.delete();
    }

    /**
     * 解析字符串模板，并返回生成的文件内容
     * @param template : 字符串模板
     * @param model : 数据
     * @return 解析后内容
     */
    public String parseProcess(String template, Object model)
            throws Exception {
        //解析模板，并处理生成新文件
        return FreeMarkerTemplateUtils.processTemplateIntoString(new Template(null, new StringReader(template), configuration),model);
    }

    /**
     * 解析模板，并返回生成的文件内容
     * @param template : 字符串模板
     * @param model : 数据
     * @return 解析后内容
     */
    public String process(Template template, Object model)
            throws Exception {
        //解析模板，并处理生成新文件
        return FreeMarkerTemplateUtils.processTemplateIntoString(template,model);
    }

    /**
     * 解析模板，并将生成的文件内容写入本地
     * @param template : 字符串模板
     * @param model : 数据
     */
    public void processDir(Template template, Object model,String path,String fileName)
            throws Exception {
        //创建文件
        File file = new File(path,fileName);
        if(file.exists()){
            file.mkdirs();
        }
        //处理生成新文件，并写入指定路径
        FileWriter fileWriter = new FileWriter(file);
        template.process(model,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }
}
