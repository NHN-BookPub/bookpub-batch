package com.nhnacademy.bookpubbatch.utils;

import com.nhnacademy.bookpubbatch.config.KeyConfig;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

/**
 * Properties 값들을 처리하는 Utils 클래스
 *
 * @author : 유호철
 * @since : 1.0
 **/

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MakeProperties {
    public static DataSourceProperties makeProperties(KeyConfig keyConfig, String url, String username, String password) {
        DataSourceProperties properties = new DataSourceProperties();
        properties.setDriverClassName(com.mysql.cj.jdbc.Driver.class.getName());
        properties.setUrl(keyConfig.keyStore(url));
        properties.setUsername(keyConfig.keyStore(username));
        properties.setPassword(keyConfig.keyStore(password));

        return properties;
    }
}
