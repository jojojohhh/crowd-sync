package com.osci.crowdsync.service.impl;

import com.osci.crowdsync.config.properties.AtlassianProperties;
import com.osci.crowdsync.dto.CrowdUserDto;
import com.osci.crowdsync.dto.SysUserDto;
import com.osci.crowdsync.dto.SysUserIdDto;
import com.osci.crowdsync.entity.SysUser;
import com.osci.crowdsync.repository.SysUserRepository;
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
import java.util.stream.Stream;

@Log4j2
@Service
@RequiredArgsConstructor
public class CrowdUserServiceImpl implements CrowdUserService {

    private final RestTemplate restTemplate;
    private final AtlassianProperties atlassianProperties;
    private final String CROWD_USER_REST_API_URL = "/rest/usermanagement/1/user";
    private final SysUserRepository sysUserRepository;
    private final UpdatedUserRepository updatedUserRepository;

    @Override
    public SysUserDto findUserById(SysUserIdDto id) {
        return sysUserRepository.findById(id.getCorpCode(), id.getUserId())
                    .map(SysUserDto::new)
                    .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Stream<SysUserDto> findAllUsers(){
        return sysUserRepository.streamAll().map(SysUserDto::new);
    }

    @Override
    @Transactional
    public List<SysUser> findAllNotUpdated() {
        return sysUserRepository.streamAllNotUpdated();
    }

    @Override
    public void getCrowdUser() {
        HttpHeaders httpHeaders = getDefaultHeaders();
        //restTemplate.getForEntity()
    }

    @Override
    public void updateCrowdUser(CrowdUserDto userDto) {
        HttpHeaders httpHeaders = getDefaultHeaders();
        HttpEntity<?> httpEntity = new HttpEntity<>(userDto, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(atlassianProperties.getCrowd().getUrl() + CROWD_USER_REST_API_URL + "?username=" + userDto.getName(), HttpMethod.PUT, httpEntity, String.class);
        log.info("status code : " + responseEntity.getStatusCodeValue());
    }

    @Override
    public void createCrowdUser(CrowdUserDto userDto) {
        HttpHeaders httpHeaders = getDefaultHeaders();
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
