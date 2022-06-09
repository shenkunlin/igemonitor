package com.itheima.ige.framework.doc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class Knife4jConfiguration {

   @Bean
   public Docket createRestApi() {
      return new Docket(DocumentationType.SWAGGER_2)
              .useDefaultResponseMessages(false)
              .apiInfo(apiInfo())
              .select()
              .apis(RequestHandlerSelectors.basePackage("com.itheima.ige"))
              .paths(PathSelectors.any())
              .build();
   }

   private ApiInfo apiInfo() {
      return new ApiInfoBuilder()
              .description("knife4j在线API接口文档-网站静态化引擎监控中心")
              .contact(new Contact("ige", "http://www.itheima.com", "shenkunlin@itcast.cn"))
              .version("v3.0.0")
              .title("网站静态化引擎监控中心")
              .build();
   }
}