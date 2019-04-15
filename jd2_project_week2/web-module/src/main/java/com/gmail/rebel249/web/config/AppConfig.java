package com.gmail.rebel249.web.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan(basePackages = {
        "com.gmail.rebel249.repository",
        "com.gmail.rebel249.service",
        "com.gmail.rebel249.web"})
@EnableAspectJAutoProxy
public class AppConfig {
}