package com.gmail.rebel249.repository.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationManager {

    @Value("${database.driver}")
    public String driver;
    @Value("${database.url}")
    public String url;
    @Value("${database.username}")
    public String username;
    @Value("${database.password}")
    public String password;

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
