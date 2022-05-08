package com.osci.crowdsync.service;

import com.osci.crowdsync.dto.CrowdUserDto;
import com.osci.crowdsync.dto.SysUserDto;
import com.osci.crowdsync.dto.SysUserIdDto;
import com.osci.crowdsync.entity.UpdatedUser;

import java.util.stream.Stream;

public interface CrowdUserService {

    SysUserDto findUserById(SysUserIdDto id);

    Stream<SysUserDto> findAllUsers();

    Stream<UpdatedUser> findAllNotUpdated();

    void getCrowdUser();

    void updateCrowdUser(CrowdUserDto userDto);

    void createCrowdUser(CrowdUserDto userDto);
}
