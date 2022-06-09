package com.itheima;
import com.alibaba.fastjson.parser.ParserConfig;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author：
 * @Description：
 ***/
@SpringBootApplication
@MapperScan(basePackages = "com.itheima.ige.monitor.mapper")
@EnableAsync
@EnableScheduling
public class IgeApplication {

    public static void main(String[] args) {
        ParserConfig.getGlobalInstance().addAccept("com.itheima.ige");
        SpringApplication.run(IgeApplication.class,args);
    }

    /**
     * MyBatisPlus分页插件
     * @return
     */
    @Bean
    public MybatisPlusInterceptor pageInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
