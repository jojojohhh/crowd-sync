package com.osci.crowdsync.service.impl;

import com.osci.crowdsync.config.properties.AtlassianProperties;
import com.osci.crowdsync.dto.CrowdUserDto;
import com.osci.crowdsync.dto.CrowdUsernameCustomDto;
import com.osci.crowdsync.dto.SysUserDto;
import com.osci.crowdsync.dto.UpdatedUserDto;
import com.osci.crowdsync.entity.CrowdUsernameCustom;
import com.osci.crowdsync.entity.UpdatedUser;
import com.osci.crowdsync.repository.CrowdUsernameCustomRepository;
import com.osci.crowdsync.repository.UpdatedUserRepository;
import com.osci.crowdsync.service.CrowdUserService;
import com.osci.crowdsync.utils.HttpHeaderBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CrowdUserService 의 구현 클래스
 * SysUser Entity 와 1:1 매핑되어 있고, Crowd Rest API 호출하는 메소드를 제공한다.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class CrowdUserServiceImpl implements CrowdUserService {

    private final RestTemplate restTemplate;
    private final AtlassianProperties atlassianProperties;
    private final String CROWD_USER_REST_API_URI = "/rest/usermanagement/1/user";
    private final UpdatedUserRepository updatedUserRepository;
    private final CrowdUsernameCustomRepository crowdUsernameCustomRepository;


    public void saveUser(UpdatedUserDto updatedUserDto, CrowdUsernameCustomDto usernameCustomDto) {
        if (usernameCustomDto == null)  save(updatedUserDto);
        else save(updatedUserDto, usernameCustomDto);
    }

    @Transactional
    public void save(UpdatedUserDto updatedUserDto) {
        updatedUserRepository.save(new UpdatedUser(updatedUserDto));
    }

    @Transactional
    public void save(UpdatedUserDto updatedUserDto, CrowdUsernameCustomDto usernameCustomDto) {
        updatedUserRepository.save(new UpdatedUser(updatedUserDto));
        crowdUsernameCustomRepository.save(new CrowdUsernameCustom(usernameCustomDto));
    }

    /**
     * Crowd Get User Rest API 호출 결과를 리턴한다.
     * @param username - Crowd 사용자의 username
     * @return ResponseEntity<CrowdUserDto>
     */
    public ResponseEntity<CrowdUserDto> getCrowdUser(String username) {
        HttpHeaders httpHeaders = getDefaultHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);
        final String URI = CROWD_USER_REST_API_URI + "?username=" + username;
        log.info("Call Crowd Rest API : " + "{method=GET, uri=" + URI + "}");
        return callCrowdRestApi(atlassianProperties.getCrowd().getUrl() + URI, HttpMethod.GET, httpEntity);
    }

    /**
     * Crowd update user Rest API 호출 결과를 리턴한다.
     * @param userDto - Crowd update user Rest API Request Body
     * @return ResponseEntity<CrowdUserDto>
     */
    public ResponseEntity<CrowdUserDto> updateCrowdUser(CrowdUserDto userDto) {
        HttpHeaders httpHeaders = getDefaultHeaders();
        HttpEntity<CrowdUserDto> httpEntity = new HttpEntity<>(userDto, httpHeaders);
        final String URI = CROWD_USER_REST_API_URI + "?username=" + userDto.getName();
        log.info("Call Crowd Rest API : " + "{method=PUT, uri=" + URI + "}");
        return callCrowdRestApi(atlassianProperties.getCrowd().getUrl() + URI, HttpMethod.PUT, httpEntity);
    }

    /**
     * Crowd add user Rest API 호출 결과를 리턴한다.
     * @param userDto - Crowd add user Rest API Request Body
     * @return ResponseEntity<CrowdUserDto>
     */
    public ResponseEntity<CrowdUserDto> createCrowdUser(CrowdUserDto userDto) {
        HttpHeaders httpHeaders = getDefaultHeaders();
        HttpEntity<CrowdUserDto> httpEntity = new HttpEntity<>(userDto, httpHeaders);
        final String URI = CROWD_USER_REST_API_URI;
        log.info("Call Crowd Rest API : " + "{method=POST, uri=" + URI + "}");
        return callCrowdRestApi(atlassianProperties.getCrowd().getUrl() + URI, HttpMethod.POST, httpEntity);
    }

    /**
     * Crowd delete Rest API 호출 결과를 리턴한다.
     * @param username - query parameter
     * @return ResponseEntity<CrowdUserDto>
     */
    public ResponseEntity<CrowdUserDto> deleteCrowdUser(String username) {
        HttpHeaders httpHeaders = getDefaultHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);
        final String URI = CROWD_USER_REST_API_URI + "?username=" + username;
        log.info("Call Crowd Rest API : " + "{method=DELETE, uri=" + URI + "}");
        return callCrowdRestApi(atlassianProperties.getCrowd().getUrl() + URI, HttpMethod.DELETE, httpEntity);
    }

    /**
     * RestTemplate 클래스를 활용하여 Crowd Rest API 를 호출하는 메소드
     * @param uri - Rest API uri
     * @param method - HttpMethod type (ex. GET, POST, PUT, DELETE)
     * @param httpEntity - Rest API Request HttpEntity, Header 정보와 Request Body 정보가 포함되어있음
     * @return ResponseEntity<CrowdUserDto>
     */
    public ResponseEntity<CrowdUserDto> callCrowdRestApi(String uri, HttpMethod method, HttpEntity httpEntity) {
        ResponseEntity<CrowdUserDto> responseEntity;
        try {
            responseEntity = restTemplate.exchange(uri, method, httpEntity, CrowdUserDto.class);
            log.info("status_code=" + responseEntity.getStatusCodeValue());
            if (!responseEntity.getStatusCode().equals(HttpStatus.NO_CONTENT))   log.info("response_body=" + responseEntity.getBody().toString());
            return responseEntity;
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            log.warn("exception_message=" + e.getMessage());
            return responseEntity;
        }
    }

    /**
     * 기본 HttpHeaders 를 가져오는 메소드
     * Crowd Application jira 에 대한 Basic Auth 를 세팅한다.
     * @return HttpHeaders
     */
    public HttpHeaders getDefaultHeaders() {
        return HttpHeaderBuilder.builder()
                .mediaType(MediaType.APPLICATION_JSON)
                .basicAuth(atlassianProperties.getApplication().getUsername(), atlassianProperties.getApplication().getPassword())
                .cacheControl("no-cache")
                .build();
    }
}
