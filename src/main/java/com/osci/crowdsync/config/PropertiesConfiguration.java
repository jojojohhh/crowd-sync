package com.osci.crowdsync.config;

import com.osci.crowdsync.config.properties.AtlassianProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {AtlassianProperties.class})
public class PropertiesConfiguration {
}
