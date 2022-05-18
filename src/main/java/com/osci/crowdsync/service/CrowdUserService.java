package com.osci.crowdsync.service;

import com.osci.crowdsync.dto.CrowdUserDto;
import com.osci.crowdsync.dto.CrowdUsernameCustomDto;
import com.osci.crowdsync.dto.SysUserDto;
import com.osci.crowdsync.dto.UpdatedUserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CrowdUserService {

    List<SysUserDto> getAll();

    void saveUser(UpdatedUserDto updatedUserDto, CrowdUsernameCustomDto usernameCustomDto);

    void save(UpdatedUserDto updatedUserDto);

    void save(UpdatedUserDto updatedUserDto, CrowdUsernameCustomDto usernameCustomDto);

    ResponseEntity<CrowdUserDto> getCrowdUser(String username);

    ResponseEntity<CrowdUserDto> updateCrowdUser(CrowdUserDto userDto);

    ResponseEntity<CrowdUserDto> createCrowdUser(CrowdUserDto userDto);
}
