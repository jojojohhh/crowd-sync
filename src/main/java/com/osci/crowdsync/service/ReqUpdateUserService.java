package com.osci.crowdsync.service;

import com.osci.crowdsync.dto.ReqUpdateUserDto;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ReqUpdateUserService {

    List<ReqUpdateUserDto> findAllReqUpdateUsers();

    ReqUpdateUserDto updateUserForUpdated(ReqUpdateUserDto reqUpdateUserDto);

    ReqUpdateUserDto updateUser(ReqUpdateUserDto reqUpdateUserDto);
}
