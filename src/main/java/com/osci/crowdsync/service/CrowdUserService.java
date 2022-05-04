package com.osci.crowdsync.service;

import com.osci.crowdsync.dto.CrowdUserDto;
import com.osci.crowdsync.dto.SysUserDto;
import com.osci.crowdsync.dto.SysUserIdDto;

import java.util.List;

public interface CrowdUserService {

    SysUserDto findUserById(SysUserIdDto id);

    List<SysUserDto> findAllUsers();
    public void updateUser(CrowdUserDto userDto);

    public void createUser(CrowdUserDto userDto);
}
