package com.itheima.ige.rpc.client.proxy;

import com.itheima.ige.rpc.annotation.Client;
import com.itheima.ige.rpc.config.ClientConfig;
import org.apache.commons.collections.CollectionUtils;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Set;

@Configuration
public class ProxyRegister implements BeanDefinitionRegistryPostProcessor, EnvironmentAware  {

    private Environment environment;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        //1、扫描有@RpcClient注解对应的包
        String packages = environment.getProperty(ClientConfig.KEY_SCANS)+","+ClientConfig.KEY_RPC_CLIENT;
        Reflections reflections = new Reflections(packages.split(","));
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Client.class);

        if(!CollectionUtils.isEmpty(typesAnnotatedWith)){
            //获取容器对象
            for (Class<?> cls : typesAnnotatedWith) {
                GenericBeanDefinition definition = (GenericBeanDefinition)BeanDefinitionBuilder.genericBeanDefinition(cls).getRawBeanDefinition();
                definition.getConstructorArgumentValues().addGenericArgumentValue(cls);
                definition.setBeanClass(ServiceFactory.class);
                //这里采用的是byType方式注入，类似的还有byName等
                definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
                registry.registerBeanDefinition(cls.getSimpleName(), definition);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
