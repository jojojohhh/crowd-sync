package com.osci.crowdsync.service;

import com.osci.crowdsync.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CrowdUserService {

    List<UserDto> getUserReqUpdate();

    List<InactiveUserDto> getInactiveUsers();

    void saveUser(UpdatedUserDto updatedUserDto, CrowdUsernameCustomDto usernameCustomDto);

    ResponseEntity<CrowdUserDto> getCrowdUser(String username);

    ResponseEntity<CrowdUserDto> updateCrowdUser(CrowdUserDto userDto);

    ResponseEntity<CrowdUserDto> createCrowdUser(CrowdUserDto userDto);

    ResponseEntity<CrowdUserDto> deleteCrowdUser(String username);
}
