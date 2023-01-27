package com.nhnacademy.bookpubbatch.config;

import static com.nhnacademy.bookpubbatch.utils.MakeProperties.makeProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Shop 에서 DB Properties 정보를 처리하기위한 Config 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Configuration
@ConfigurationProperties("book-pub.shop")
@RequiredArgsConstructor
public class ShopPropertiesConfig {
    private final KeyConfig keyConfig;
    private String url;

    private String username;

    private String password;

    @Bean
    public DataSourceProperties shopDataSourceProperties() {
        return makeProperties(keyConfig, url, username, password);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
