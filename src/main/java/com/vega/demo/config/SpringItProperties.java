package com.vega.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("springIt")
public class SpringItProperties {
    private String welcomeMsg = "You have logged into my wonderful world";

    public String getWelcomeMsg() {
        return welcomeMsg;
    }

    public void setWelcomeMsg(String welcomeMsg) {
        this.welcomeMsg = welcomeMsg;
    }
}
