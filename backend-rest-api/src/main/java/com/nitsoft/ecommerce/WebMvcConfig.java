/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce;

/**
 *
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{
    @Autowired
    ObjectMapper objectMapper;
    
    @Bean
    public WebMvcConfigurerAdapter forwardToIndex() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                // forward requests to /admin and /user to their index.html
                registry.addViewController("/api_docs").setViewName(
                        "forward:/api_docs/index.html");
                
//                registry.addViewController("/").setViewName(
//                        "forward:/api_docs/index.html");
            }
        };
    }
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(new AuthSessionResolver(userTokenServiceImpl));
//        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jacksonMessageConverter());
        super.configureMessageConverters(converters);
    }

    /**
     * Register the Hibernate4Module into an ObjectMapper, then set this
     * custom-configured ObjectMapper to the MessageConverter and return it to
     * be added to the HttpMessageConverters of our application
     *
     * @return
     */
    public MappingJackson2HttpMessageConverter jacksonMessageConverter() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        //Registering Hibernate4Module to support lazy objects
        Hibernate4Module module = new Hibernate4Module();
        //module.disable(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION);
        objectMapper.registerModule(module);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);

        messageConverter.setObjectMapper(objectMapper);
        return messageConverter;
    }

}
