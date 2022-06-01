package com.osci.crowdsync.config;

import com.osci.crowdsync.config.properties.AtlassianProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final AtlassianProperties atlassianProperties;

    @Bean
    public WebClient webClient() {
        return WebClient
                .builder()
                .baseUrl(atlassianProperties.getCrowd().getUrl())
                .codecs(ClientCodecConfigurer::defaultCodecs)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setCacheControl("no-cache");
                    httpHeaders.setBasicAuth(atlassianProperties.getApplication().getUsername(), atlassianProperties.getApplication().getPassword());
                })
                .build();
    }
}
