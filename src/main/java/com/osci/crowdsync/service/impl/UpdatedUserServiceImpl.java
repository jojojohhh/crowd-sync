package com.osci.crowdsync.service.impl;

import com.osci.crowdsync.dto.UpdatedUserDto;
import com.osci.crowdsync.entity.UpdatedUser;
import com.osci.crowdsync.repository.UpdatedUserRepository;
import com.osci.crowdsync.service.UpdatedUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UpdatedUserServiceImpl implements UpdatedUserService {

    private final UpdatedUserRepository updatedUserRepository;

    @Transactional
    public UpdatedUser save(UpdatedUserDto updatedUserDto) {
        return updatedUserRepository.save(new UpdatedUser(updatedUserDto));
    }
}
