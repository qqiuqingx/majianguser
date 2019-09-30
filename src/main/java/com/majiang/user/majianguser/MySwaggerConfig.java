package com.majiang.user.majianguser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
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
                .apiInfo(apiInfo())
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
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("swagger")
                .termsOfServiceUrl("麻将")
                .version("版本号：2.0")
                .build();
    }
}
