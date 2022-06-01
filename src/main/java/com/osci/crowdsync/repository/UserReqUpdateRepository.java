package com.osci.crowdsync.repository;

import com.osci.crowdsync.dto.InactiveUserDto;
import com.osci.crowdsync.dto.SysUserDto;
import com.osci.crowdsync.dto.UserDto;

import java.util.List;

public interface UserReqUpdateRepository {
    List<UserDto> findUserRequireUpdate();

    List<InactiveUserDto> findInactiveUser();
}
