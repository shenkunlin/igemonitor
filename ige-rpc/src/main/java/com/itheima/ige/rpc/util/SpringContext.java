package com.itheima.ige.rpc.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    public static <T> T getBean(Class<T> cls) {
        return context.getBean(cls);
    }

    public static <T> T getBean(String name) {
        if(context.containsBean(name)){
            return (T) context.getBean(name);
        }
        return null;
    }

    /***
     * 获取有指定注解的对象
     * @param annotationClass
     * @return
     */
    public static Map<String, Object> getBeanListByAnnotationClass(Class<? extends Annotation> annotationClass) {
         return context.getBeansWithAnnotation(annotationClass);
    }

    /***
     * 获取容器对象，可以将自己定义的对象的实例添加到容器中
     * @return
     */
    public static ApplicationContext context() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}