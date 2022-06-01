package com.osci.crowdsync.service;

import com.osci.crowdsync.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CrowdUserService {

    List<UserDto> getUserReqUpdate();

    List<InactiveUserDto> getInactiveUsers();

    void saveUser(UpdatedUserDto updatedUserDto, CrowdUsernameCustomDto usernameCustomDto);

    ResponseEntity<CrowdUserDto> getCrowdUserByWebClient(String username);

    ResponseEntity<CrowdUserDto> updateCrowdUserByWebClient(CrowdUserDto crowdUserDto);

    ResponseEntity<CrowdUserDto> createCrowdUserByWebClient(CrowdUserDto crowdUserDto);

    ResponseEntity<CrowdUserDto> deleteCrowdUserByWebClient(String username);

}
