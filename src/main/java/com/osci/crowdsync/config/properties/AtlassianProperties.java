package com.osci.crowdsync.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;


/**
 * atlassian property binding class
 * 모든 변수 또는 하위 property 는 상수로 선언
 */
@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties("atlassian")
public class AtlassianProperties {

    private final Application application;
    private final Crowd crowd;


    @Getter
    @RequiredArgsConstructor
    public static final class Application {
        private final String username;
        private final String password;
    }

    @Getter
    @RequiredArgsConstructor
    public static final class Crowd {
        private final String url;
    }
}
