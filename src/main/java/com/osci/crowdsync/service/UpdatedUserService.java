package com.osci.crowdsync.service;

import com.osci.crowdsync.dto.UpdatedUserDto;
import com.osci.crowdsync.entity.UpdatedUser;

public interface UpdatedUserService {
    UpdatedUser save(UpdatedUserDto updatedUser);
}
