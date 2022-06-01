package com.osci.crowdsync.service.impl;

import com.osci.crowdsync.config.properties.AtlassianProperties;
import com.osci.crowdsync.dto.*;
import com.osci.crowdsync.entity.CrowdUsernameCustom;
import com.osci.crowdsync.entity.UpdatedUser;
import com.osci.crowdsync.repository.CrowdUsernameCustomRepository;
import com.osci.crowdsync.repository.UpdatedUserRepository;
import com.osci.crowdsync.repository.UserReqUpdateRepository;
import com.osci.crowdsync.service.CrowdUserService;
import com.osci.crowdsync.utils.HttpHeaderBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import static org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

/**
 * CrowdUserService 의 구현 클래스
 * Crowd Rest API 호출하는 메소드를 제공하며,
 * userReqUpdateRepository, crowdUsernameCustomRepository, updatedUserRepository 들의 메소드를 다루는 클래스
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class CrowdUserServiceImpl implements CrowdUserService {
    private final WebClient webClient;
    private final String CROWD_USER_REST_API_URI = "/rest/usermanagement/1/user";
    private final UpdatedUserRepository updatedUserRepository;
    private final CrowdUsernameCustomRepository crowdUsernameCustomRepository;
    private final UserReqUpdateRepository userReqUpdateRepository;


    @Transactional
    public List<UserDto> getUserReqUpdate() {
        return userReqUpdateRepository.findUserRequireUpdate();
    }

    @Transactional
    public List<InactiveUserDto> getInactiveUsers() {
        return userReqUpdateRepository.findInactiveUser();
    }

    /**
     * 파라미터의 UpdatedUser, CrowdUsernameCustom Upsert
     * @param updatedUserDto
     * @param usernameCustomDto
     */
    @Transactional
    public void saveUser(UpdatedUserDto updatedUserDto, CrowdUsernameCustomDto usernameCustomDto) {
        updatedUserRepository.save(new UpdatedUser(updatedUserDto));
        if (usernameCustomDto != null)  crowdUsernameCustomRepository.save(new CrowdUsernameCustom(usernameCustomDto));
    }


    /**
     * Crowd Get User Rest API 호출 결과를 리턴한다.
     * @param username - Crowd 사용자의 username
     * @return ResponseEntity<CrowdUserDto>
     */
    public ResponseEntity<CrowdUserDto> getCrowdUserByWebClient(String username) {
        final String URI = CROWD_USER_REST_API_URI + "?username=" + username;
        log.info("Call Crowd Rest API : " + "{method=GET, uri=" + URI + "}");
        return callRestApi(webClient.method(HttpMethod.GET), URI);
    }

    /**
     * Crowd update user Rest API 호출 결과를 리턴한다.
     * @param crowdUserDto - Crowd update user Rest API Request Body
     * @return ResponseEntity<CrowdUserDto>
     */
    public ResponseEntity<CrowdUserDto> updateCrowdUserByWebClient(CrowdUserDto crowdUserDto) {
        final String URI = CROWD_USER_REST_API_URI;
        log.info("Call Crowd Rest API : " + "{method=POST, uri=" + URI + "}");
        return callRestApi(webClient.method(HttpMethod.PUT), URI, crowdUserDto);
    }

    /**
     * Crowd add user Rest API 호출 결과를 리턴한다.
     * @param crowdUserDto - Crowd add user Rest API Request Body
     * @return ResponseEntity<CrowdUserDto>
     */
    public ResponseEntity<CrowdUserDto> createCrowdUserByWebClient(CrowdUserDto crowdUserDto) {
        final String URI = CROWD_USER_REST_API_URI;
        log.info("Call Crowd Rest API : " + "{method=POST, uri=" + URI + "}");
        return callRestApi(webClient.method(HttpMethod.POST), URI, crowdUserDto);
    }

    /**
     * Crowd delete Rest API 호출 결과를 리턴한다.
     * @param username - query parameter
     * @return ResponseEntity<CrowdUserDto>
     */
    public ResponseEntity<CrowdUserDto> deleteCrowdUserByWebClient(String username) {
        final String URI = CROWD_USER_REST_API_URI + "?username=" + username;
        log.info("Call Crowd Rest API : " + "{method=DELETE, uri=" + URI + "}");
        return callRestApi(webClient.method(HttpMethod.DELETE), URI);
    }

    /**
     * WebClient 클래스를 활용하여 Crowd Rest API 를 호출하는 메소드
     * @param bodyUriSpec - WebClient 의 method 의 리턴 값
     * @param uri - Rest API uri
     * @return ResponseEntity<CrowdUserDto>
     */
    public ResponseEntity<CrowdUserDto> callRestApi(RequestBodyUriSpec bodyUriSpec, String uri) {
        ResponseEntity<CrowdUserDto> responseEntity;
        try {
            responseEntity = bodyUriSpec.uri(uri)
                    .retrieve()
                    .toEntity(CrowdUserDto.class)
                    .block();
            log.info("status_code=" + responseEntity.getStatusCodeValue());
            if (!responseEntity.getStatusCode().equals(HttpStatus.NO_CONTENT))   log.info("response_body=" + responseEntity.getBody().toString());
            return responseEntity;
        } catch (WebClientResponseException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.valueOf(e.getStatusCode().value()));
            log.warn("exception_message=" + e.getMessage());
            return responseEntity;
        }
    }

    /**
     * WebClient 클래스를 활용하여 Crowd Rest API 를 호출하는 메소드
     * @param bodyUriSpec - WebClient 의 method 의 리턴 값
     * @param uri - Rest API uri
     * @param crowdUserDto - Request Body
     * @return ResponseEntity<CrowdUserDto>
     */
    public ResponseEntity<CrowdUserDto> callRestApi(RequestBodyUriSpec bodyUriSpec, String uri, CrowdUserDto crowdUserDto) {
        ResponseEntity<CrowdUserDto> responseEntity;
        try {
            responseEntity = bodyUriSpec.uri(uri)
                    .bodyValue(crowdUserDto)
                    .retrieve()
                    .toEntity(CrowdUserDto.class)
                    .block();
            log.info("status_code=" + responseEntity.getStatusCodeValue());
            if (!responseEntity.getStatusCode().equals(HttpStatus.NO_CONTENT))   log.info("response_body=" + responseEntity.getBody().toString());
            return responseEntity;
        } catch (WebClientResponseException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.valueOf(e.getStatusCode().value()));
            log.warn("exception_message=" + e.getMessage());
            return responseEntity;
        }
    }
}
