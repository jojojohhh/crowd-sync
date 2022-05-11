package com.osci.crowdsync.service;

import com.osci.crowdsync.dto.CrowdUserDto;
import com.osci.crowdsync.dto.SysUserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CrowdUserService {

    List<SysUserDto> findAllNotUpdated();

    ResponseEntity<CrowdUserDto> getCrowdUser(String username);

    ResponseEntity<CrowdUserDto> updateCrowdUser(CrowdUserDto userDto);

    ResponseEntity<CrowdUserDto> createCrowdUser(CrowdUserDto userDto);
}
