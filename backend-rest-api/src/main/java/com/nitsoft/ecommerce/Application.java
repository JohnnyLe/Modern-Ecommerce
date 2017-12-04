package com.nitsoft.ecommerce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.ApiInfoBuilder;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/*
 * This is the main Spring Boot application class. It configures Spring Boot, JPA, Swagger
 */
@EnableAutoConfiguration  // Sprint Boot Auto Configuration
@ComponentScan(basePackages = "com.nitsoft.ecommerce")
@EnableSwagger2 // auto generation of API docs
public class Application extends SpringBootServletInitializer {

    private static final Class<Application> APPLICATION_NAME = Application.class;
    private final Logger logger = LoggerFactory.getLogger(APPLICATION_NAME);

    public static void main(String[] args) {
        SpringApplication.run(APPLICATION_NAME, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(APPLICATION_NAME);
    }

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("apis")
                .apiInfo(apiInfo())
                .select()
                .paths(regex("/api.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Ecommerce Platform REST API Documents")
                .description("Documents with Swagger 2")
                .termsOfServiceUrl("http://nit-software.com")
                .contact("")
                .license("")
                .licenseUrl("")
                .version("2.0")
                .build();
    }
}
