package com.osci.crowdsync.service.impl;

import com.osci.crowdsync.dto.UpdatedUserDto;
import com.osci.crowdsync.entity.UpdatedUser;
import com.osci.crowdsync.repository.UpdatedUserRepository;
import com.osci.crowdsync.service.UpdatedUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UpdatedUserService 의 구현 클래스
 * UpdatedUser Entity 와 1:1 매핑되어 있다.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class UpdatedUserServiceImpl implements UpdatedUserService {

    private final UpdatedUserRepository updatedUserRepository;

    /**
     * 파라미터의 UpdatedUserDto 에 따라 Update, Insert 를 하는 메소드
     * @param updatedUserDto - corpCode, userId is NOT NULL
     * @return UpdatedUserDto
     */
    @Transactional
    public UpdatedUserDto save(UpdatedUserDto updatedUserDto) {
        log.info("Update updated_user table. UpdatedUser(corp_code=" + updatedUserDto.getCorpCode() + ", user_id=" + updatedUserDto.getUserId() + ")");
        return new UpdatedUserDto(updatedUserRepository.save(new UpdatedUser(updatedUserDto)));
    }
}
