package com.osci.crowdsync.service.impl;

import com.osci.crowdsync.dto.ReqUpdateUserDto;
import com.osci.crowdsync.entity.ReqUpdateUser;
import com.osci.crowdsync.repository.ReqUpdateUserRepository;
import com.osci.crowdsync.service.ReqUpdateUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReqUpdateUserServiceImpl implements ReqUpdateUserService {

    private final ReqUpdateUserRepository reqUpdateUserRepository;

    @Transactional
    public List<ReqUpdateUserDto> findAllReqUpdateUsers() {
        return reqUpdateUserRepository.findAllReqUpdateUsers().map(ReqUpdateUserDto::new).collect(Collectors.toList());
    }

    @Transactional
    public ReqUpdateUserDto updateUser(ReqUpdateUserDto reqUpdateUserDto) {
        return new ReqUpdateUserDto(reqUpdateUserRepository.save(ReqUpdateUser.builder()
                .userId(reqUpdateUserDto.getUserId())
                .displayName(reqUpdateUserDto.getDisplayName())
                .deptCode(reqUpdateUserDto.getDeptCode())
                .updated('1')
                .build()));
    }
}
