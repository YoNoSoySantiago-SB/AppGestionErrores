package com.segurosbolivar.refactoring.errors.config;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.segurosbolivar.refactoring.errors.GlobalExceptionHandler;

@Configuration
@ComponentScan("com.segurosbolivar.refactoring.errors")
@PropertySource("classpath:library.properties")
public class GlobalExceptionHandlerConfig {
	@Autowired
	private Environment env;

    @Bean
    DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();

        dataSourceBuilder.url(env.getProperty("spring.datasource.url"));
        dataSourceBuilder.username(env.getProperty("spring.datasource.username"));
        dataSourceBuilder.password(env.getProperty("spring.datasource.password"));
        dataSourceBuilder.driverClassName(env.getProperty("spring.datasource.driver-class-name"));
        return dataSourceBuilder.build();
    }
    
    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
