package com.zoe.snow.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * RestApiConfig
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/3/21
 */

    /*
     * Restful API 访问路径:
     * http://IP:port/{context-path}/swagger-ui.html
     * eg:http://localhost:8080/jd-config-web/swagger-ui.html
     */
@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages = {"com.zoe.snow.swagger.ctrl"})
@Configuration
public class RestApiConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zoe.snow.swagger"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("QoS APIs")
                .termsOfServiceUrl("")
                .contact("QUANTIL,INC.")
                .version("1.1")
                .build();
    }
}
