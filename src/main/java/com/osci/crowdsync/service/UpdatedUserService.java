package com.osci.crowdsync.service;

import com.osci.crowdsync.dto.UpdatedUserDto;

public interface UpdatedUserService {
    UpdatedUserDto save(UpdatedUserDto updatedUserDto);
}
