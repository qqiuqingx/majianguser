package com.majiang.user.majianguser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class MySwaggerConfig {

    @Bean
    public Docket createRestApi() {
        System.out.println("swagger配置类》》》》》》》》》》》》》》》》》》》》》");
        return new Docket(DocumentationType.SWAGGER_2)
                //详细的定制信息
                .apiInfo(apiInfo("麻将馆","1.0"))
                //分组 因为程序的编码是gbk，所以如果这里设置为中文的话会有问题
                .groupName("test")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.majiang.user.majianguser.controller"))//API包路径
                // 扫描所有
                //.apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 添加摘要信息
     * @return
     */
    private ApiInfo apiInfo(String title,String version) {
        return new ApiInfoBuilder()
                .title(title)
                .description("接口信息")
                .termsOfServiceUrl("https://github.com/qqiuqingx/majianguser")
                .contact(new Contact("qqiuqingx","https://github.com/qqiuqingx/majianguser","qqiuqingx@163.com"))
                .version(version)
                .build();
    }
}
