package com.osci.crowdsync.service;

import com.osci.crowdsync.dto.*;
import com.osci.crowdsync.entity.CrowdUsernameCustom;
import com.osci.crowdsync.entity.UpdatedUser;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CrowdUserService {

    List<UserDto> getUserReqUpdate();

    List<InactiveUserDto> getInactiveUsers();

    void saveUser(List<UpdatedUser> updatedUsers, List<CrowdUsernameCustom> usernameCustoms);

    ResponseEntity<CrowdUserDto> getCrowdUserByWebClient(String username);

    ResponseEntity<CrowdUserDto> updateCrowdUserByWebClient(CrowdUserDto crowdUserDto);

    ResponseEntity<CrowdUserDto> createCrowdUserByWebClient(CrowdUserDto crowdUserDto);

    ResponseEntity<CrowdUserDto> deleteCrowdUserByWebClient(String username);

}
