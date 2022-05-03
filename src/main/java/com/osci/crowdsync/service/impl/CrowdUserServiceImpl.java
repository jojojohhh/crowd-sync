package com.osci.crowdsync.service.impl;

import com.osci.crowdsync.config.properties.AtlassianProperties;
import com.osci.crowdsync.dto.CrowdUserDto;
import com.osci.crowdsync.service.CrowdUserService;
import com.osci.crowdsync.utils.HttpHeaderBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Log4j2
@Service
@RequiredArgsConstructor
public class CrowdUserServiceImpl implements CrowdUserService {

    private final RestTemplate restTemplate;
    private final AtlassianProperties atlassianProperties;
    private final String CROWD_USER_REST_API_URL = "/rest/usermanagement/1/user";

    @Override
    public void updateUser() {
        HttpHeaders httpHeaders = getDefaultHeaders();
        CrowdUserDto userDto = CrowdUserDto.builder()
                .name("test1234")
                .firstName("sangje")
                .lastName("jo")
                .displayName("test-sangjejo")
                .email("sjjo@osci.kr")
                .build();
        HttpEntity<?> httpEntity = new HttpEntity<>(userDto, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(atlassianProperties.getCrowd().getUrl() + CROWD_USER_REST_API_URL, HttpMethod.PUT, httpEntity, String.class);
        log.info("status code : " + responseEntity.getStatusCodeValue());
    }

    @Override
    public void createUser() {
        HttpHeaders httpHeaders = getDefaultHeaders();
        CrowdUserDto userDto = CrowdUserDto.builder()
                .name("test1234")
                .firstName("sangje")
                .lastName("jo")
                .displayName("test-sangjejo")
                .email("sjjo@osci.kr")
                .build();
        HttpEntity<?> httpEntity = new HttpEntity<>(userDto, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(atlassianProperties.getCrowd().getUrl() + CROWD_USER_REST_API_URL, httpEntity, String.class);
        log.info("status code : " + responseEntity.getStatusCodeValue());
    }

    public HttpHeaders getDefaultHeaders() {
        return HttpHeaderBuilder.builder()
                .mediaType(MediaType.APPLICATION_JSON)
                .basicAuth(atlassianProperties.getJira().getUsername(), atlassianProperties.getJira().getPassword())
                .cacheControl("no-cache")
                .build();
    }
}
