package com.nhnacademy.bookpubbatch.config;

import static com.nhnacademy.bookpubbatch.utils.MakeProperties.makeProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DataSource 를 다루기위한 Config 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Slf4j
@Configuration
@ConfigurationProperties("book-pub.batch")
@RequiredArgsConstructor
public class BatchPropertiesConfig {
    private final KeyConfig keyConfig;
    private String url;

    private String username;

    private String password;

    /**
     * DataSourceProperties 를 만들기위한 metohd 입니다.
     *
     * @return keymanger 가 변환한 properties 로 변환됩니다.
     */
    @Bean
    public DataSourceProperties batchDataSourceProperties() {

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
