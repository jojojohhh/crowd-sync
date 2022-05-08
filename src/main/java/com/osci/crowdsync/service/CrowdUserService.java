package com.osci.crowdsync.service;

import com.osci.crowdsync.dto.CrowdUserDto;
import com.osci.crowdsync.dto.SysUserDto;
import com.osci.crowdsync.dto.SysUserIdDto;
import com.osci.crowdsync.entity.SysUser;

import java.util.List;
import java.util.stream.Stream;

public interface CrowdUserService {

    SysUserDto findUserById(SysUserIdDto id);

    Stream<SysUserDto> findAllUsers();

    List<SysUser> findAllNotUpdated();

    void getCrowdUser();

    void updateCrowdUser(CrowdUserDto userDto);

    void createCrowdUser(CrowdUserDto userDto);
}
