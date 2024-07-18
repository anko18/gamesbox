package com.gamesbox.igdbapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class ConfigProperties {
    @Value("${igdb.client_id}")
    private String client_id;

    @Value("${igdb.client_secret}")
    private String client_secret;


    public String getSecret() {
        return client_secret;
    }

    public void setSecret(String secret) {
        this.client_secret = secret;
    }

    public String getId() {
        return client_id;
    }

    public void setId(String id) {
        this.client_id = id;
    }


}
