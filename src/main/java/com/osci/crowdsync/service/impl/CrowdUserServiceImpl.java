package com.osci.crowdsync.service.impl;

import com.osci.crowdsync.config.properties.AtlassianProperties;
import com.osci.crowdsync.dto.CrowdUserDto;
import com.osci.crowdsync.dto.SysUserDto;
import com.osci.crowdsync.dto.UserIdDto;
import com.osci.crowdsync.entity.SysUser;
import com.osci.crowdsync.repository.SysUserRepository;
import com.osci.crowdsync.service.CrowdUserService;
import com.osci.crowdsync.utils.HttpHeaderBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Stream;

@Log4j2
@Service
@RequiredArgsConstructor
public class CrowdUserServiceImpl implements CrowdUserService {

    private final RestTemplate restTemplate;
    private final AtlassianProperties atlassianProperties;
    private final String CROWD_USER_REST_API_URI = "/rest/usermanagement/1/user";
    private final SysUserRepository sysUserRepository;

    @Transactional
    public SysUserDto findUserById(UserIdDto id) {
        return sysUserRepository.findById(id.getCorpCode(), id.getUserId())
                    .map(SysUserDto::new)
                    .orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public Stream<SysUserDto> findAllUsers(){
        return sysUserRepository.streamAll().map(SysUserDto::new);
    }

    @Transactional
    public List<SysUser> findAllNotUpdated() {
        return sysUserRepository.streamAllNotUpdated();
    }

    public ResponseEntity<String> getCrowdUser(String username) {
        HttpHeaders httpHeaders = getDefaultHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);
        final String URI = CROWD_USER_REST_API_URI + "?username=" + username;
        log.info("Call Crowd Rest API : " + "{method=GET, url=" + URI + "}");
        return callCrowdRestApi(atlassianProperties.getCrowd().getUrl() + URI, HttpMethod.GET, httpEntity);
    }

    public ResponseEntity<String> updateCrowdUser(CrowdUserDto userDto) {
        HttpHeaders httpHeaders = getDefaultHeaders();
        HttpEntity<CrowdUserDto> httpEntity = new HttpEntity<>(userDto, httpHeaders);
        final String URI = CROWD_USER_REST_API_URI + "?username=" + userDto.getName();
        log.info("Call Crowd Rest API : " + "{method=PUT, url=" + URI + "}");
        return callCrowdRestApi(atlassianProperties.getCrowd().getUrl() + URI, HttpMethod.PUT, httpEntity);
    }

    public ResponseEntity<String> createCrowdUser(CrowdUserDto userDto) {
        HttpHeaders httpHeaders = getDefaultHeaders();
        HttpEntity<CrowdUserDto> httpEntity = new HttpEntity<>(userDto, httpHeaders);
        final String URI = CROWD_USER_REST_API_URI;
        log.info("Call Crowd Rest API : " + "{method=POST, url=" + URI + "}");
        return callCrowdRestApi(atlassianProperties.getCrowd().getUrl() + URI, HttpMethod.POST, httpEntity);
    }

    public ResponseEntity<String> callCrowdRestApi(String uri, HttpMethod method, HttpEntity httpEntity) {
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.exchange(uri, method, httpEntity, String.class);
            log.info("status_code=" + responseEntity.getStatusCodeValue());
            log.info("response_body=" + responseEntity.getBody());
            return responseEntity;
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            log.warn("Not found crowd user");
            log.info("status_code=" + responseEntity.getStatusCodeValue());
            return responseEntity;
        }
    }

    public HttpHeaders getDefaultHeaders() {
        return HttpHeaderBuilder.builder()
                .mediaType(MediaType.APPLICATION_JSON)
                .basicAuth(atlassianProperties.getJira().getUsername(), atlassianProperties.getJira().getPassword())
                .cacheControl("no-cache")
                .build();
    }
}
