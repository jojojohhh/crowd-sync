package com.osci.crowdsync.service;

import com.osci.crowdsync.dto.ReqUpdateUserDto;

import java.util.List;

public interface ReqUpdateUserService {

    List<ReqUpdateUserDto> findAllReqUpdateUsers();

    ReqUpdateUserDto save(ReqUpdateUserDto reqUpdateUserDto);
}
