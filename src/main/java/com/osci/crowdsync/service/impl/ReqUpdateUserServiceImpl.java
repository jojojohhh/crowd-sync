package com.osci.crowdsync.service.impl;

import com.osci.crowdsync.dto.ReqUpdateUserDto;
import com.osci.crowdsync.entity.ReqUpdateUser;
import com.osci.crowdsync.repository.ReqUpdateUserRepository;
import com.osci.crowdsync.service.ReqUpdateUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ReqUpdateUserService 의 구현 클래스
 * ReqUpdateUser Entity 와 1:1 매핑되어 있다.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class ReqUpdateUserServiceImpl implements ReqUpdateUserService {

    private final ReqUpdateUserRepository reqUpdateUserRepository;

    /**
     * ReqUpdateUserRepository findAllReqUpdateUsers() 메소드의 리턴값인 Stream<ReqUpdateUser> 을 List<ReqUpdateUserDto> 으로 변환 하여준다.
     * @return List<ReqUpdateUserDto>
     */
    @Transactional
    public List<ReqUpdateUserDto> findAllReqUpdateUsers() {
        return reqUpdateUserRepository.findAllReqUpdateUsers().map(ReqUpdateUserDto::new).collect(Collectors.toList());
    }

    /**
     * 파라미터의 ReqUpdateUserDto 에 따라 Update, Insert 를 하는 메소드
     * @param reqUpdateUserDto - corpCode, userId is NOT NULL
     * @return ReqUpdateUserDto
     */
    @Transactional
    public ReqUpdateUserDto save(ReqUpdateUserDto reqUpdateUserDto) {
        log.info("Update req_update_user table. ReqUpdateUser(corp_code=" + reqUpdateUserDto.getCorpCode() + ", user_id=" + reqUpdateUserDto.getUserId() + ")");
        return new ReqUpdateUserDto(reqUpdateUserRepository.save(ReqUpdateUser.builder()
                .corpCode(reqUpdateUserDto.getCorpCode())
                .userId(reqUpdateUserDto.getUserId())
                .displayName(reqUpdateUserDto.getDisplayName())
                .deptCode(reqUpdateUserDto.getDeptCode())
                .updated(reqUpdateUserDto.getUpdated())
                .build()));
    }
}
