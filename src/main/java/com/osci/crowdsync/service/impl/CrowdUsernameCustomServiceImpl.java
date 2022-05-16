package com.osci.crowdsync.service.impl;

import com.osci.crowdsync.dto.CrowdUsernameCustomDto;
import com.osci.crowdsync.entity.CrowdUsernameCustom;
import com.osci.crowdsync.repository.CrowdUsernameCustomRepository;
import com.osci.crowdsync.service.CrowdUsernameCustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrowdUsernameCustomServiceImpl implements CrowdUsernameCustomService {

    private final CrowdUsernameCustomRepository crowdUsernameCustomRepository;

    public CrowdUsernameCustomDto save(CrowdUsernameCustomDto crowdUsernameCustomDto) {
        return new CrowdUsernameCustomDto(crowdUsernameCustomRepository.save(new CrowdUsernameCustom(crowdUsernameCustomDto)));
    }
}
