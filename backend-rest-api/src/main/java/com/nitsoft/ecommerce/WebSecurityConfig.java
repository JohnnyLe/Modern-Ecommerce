/*
 * Copyright (c) NIT-Software. All Rights Reserved.
 * This software is the confidential and proprietary information of NIT-Software,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with NIT-Software.
 */
package com.nitsoft.ecommerce;

import com.nitsoft.ecommerce.auth.AuthEntryPointException;
import com.nitsoft.ecommerce.auth.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthEntryPointException unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationTokenFilterBean() throws Exception {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // Allow access public resource
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/upload/**",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.woff",
                        "/**/*.woff2",
                        "/**/*.tff",
                        // enable swagger endpoints
                        "/swagger-resources",
                        "/configuration/ui",
                        "/configuration/security"
                ).permitAll()
                // TODO: just for testing AWS SNS, SES
                //.antMatchers("/api/v1/upload/**").permitAll()
                //.antMatchers("/ses/**").permitAll()
                // allow access login API without require token
                //.antMatchers("/manage/**").permitAll()
                // allow CORS option calls
                .antMatchers(HttpMethod.OPTIONS, "/api/v1/**").permitAll()
                .antMatchers("/api/v1/{company_id}/auth/admin/login").permitAll()
                .antMatchers("/api/v1/1/products/**").permitAll()
                .antMatchers("/api/v1/1/users/**").permitAll()
                .antMatchers("/api/v1/1/categories/**").permitAll()
                .antMatchers("/api/v1/1/orders/**").permitAll()
                .antMatchers("/api/v1/1/auth/session/data").permitAll()
                //.antMatchers("/v1/test/**").permitAll()
                //.antMatchers("/**").permitAll()
                //.antMatchers(HttpMethod.POST,"/**").permitAll()
                // All other reruest must be specify token
                .anyRequest().authenticated();

        // Custom JWT based security filter
        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        httpSecurity.headers().cacheControl();
    }
}
