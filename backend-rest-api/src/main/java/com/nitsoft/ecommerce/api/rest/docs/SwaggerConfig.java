package com.nitsoft.ecommerce.api.rest.docs;

import static springfox.documentation.builders.PathSelectors.regex;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import com.google.common.base.Predicate;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


public class SwaggerConfig {

//    public static final String DEFAULT_INCLUDE_PATTERNS = "/api/.*";
//
//    private SpringSwaggerConfig springSwaggerConfig;
//
//    @Autowired
//    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
//        this.springSwaggerConfig = springSwaggerConfig;
//    }
//
//    @Bean 
//    public SwaggerSpringMvcPlugin customImplementation() {
//        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
//                .apiInfo(apiInfo())
//                .swaggerGroup("cantool-api")
//                .includePatterns(DEFAULT_INCLUDE_PATTERNS);
//    }
//    
//
//    private ApiInfo apiInfo() {
//        ApiInfo apiInfo = new ApiInfo(
//                "CAN TOOL REST APIs",
//                "The Rest API for CAN TOOL app",
//                "canreader.com",
//                "johnny.lxa@gmail.com",
//                "APIs",
//                "APIs"
//        );
//        
//        
//        return apiInfo;
//    }
    
    
}
