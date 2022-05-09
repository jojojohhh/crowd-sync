package com.osci.crowdsync.service;

import com.osci.crowdsync.dto.CrowdUserDto;
import com.osci.crowdsync.dto.SysUserDto;
import com.osci.crowdsync.dto.UserIdDto;
import com.osci.crowdsync.entity.SysUser;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Stream;

public interface CrowdUserService {

    SysUserDto findUserById(UserIdDto id);

    Stream<SysUserDto> findAllUsers();

    List<SysUser> findAllNotUpdated();

    ResponseEntity<String> getCrowdUser(String username);

    ResponseEntity<String> updateCrowdUser(CrowdUserDto userDto);

    ResponseEntity<String> createCrowdUser(CrowdUserDto userDto);
}
