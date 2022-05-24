package com.osci.crowdsync.scheduler;

import com.osci.crowdsync.dto.CrowdUserDto;
import com.osci.crowdsync.dto.CrowdUsernameCustomDto;
import com.osci.crowdsync.dto.UpdatedUserDto;
import com.osci.crowdsync.service.CrowdUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceException;


@Log4j2
@RequiredArgsConstructor
@Component
public class CrowdSyncScheduler {

    private final CrowdUserService crowdUserService;

    /**
     * Crowd display name 을 업데이트 하기 위한 스케줄러 cron expression 으로 동작한다.
     * 기본 Display Name : 이름/직책/부서
     * 커스텀 Display Name : CROWD_USERNAME_CUSTOM 테이블의 USER_CUSTOM_NAME column 참조
     */
    @Scheduled(cron = "${scheduler.cron}")
    public void crowdSyncJob() {
        log.info("Crowd display name update scheduler start (default format : name pos_name / dept_name)");

        crowdUserService.getUserReqUpdate().forEach(userDto -> {
            boolean isCustom = false;
            if (userDto.getUpdatedUserDisplayName() == null) {
                isCustom = userDto.getUserCustomName() != null;
            } else {
                if (userDto.getUserCustomName() != null)    isCustom = userDto.isChkYn() || !userDto.getUpdatedUserDisplayName().equals(userDto.getUserCustomName());
            }

            String displayName = isCustom ? userDto.getUserCustomName() : userDto.getName() + " " + userDto.getPosName() + " / " + userDto.getDeptName();

            ResponseEntity<CrowdUserDto> res = crowdUserService.getCrowdUser(userDto.getUserId());
            CrowdUserDto resBody = res.getBody();

            log.info("User(userId=" + userDto.getUserId() + ")");
            ResponseEntity<CrowdUserDto> updateOrCreateRes;
            CrowdUserDto crowdUserDto;
            if (!res.getStatusCode().isError()) {
                log.info("Update the user display name. (before: " + resBody.getDisplayName() + " -> after: " + displayName + ")");
                crowdUserDto = CrowdUserDto.builder()
                        .name(resBody.getName())
                        .password(new CrowdUserDto.Password())
                        .firstName(userDto.getName())
                        .lastName(userDto.getName())
                        .email(userDto.getEmail())
                        .displayName(displayName)
                        .active(true)
                        .build();
                updateOrCreateRes = crowdUserService.updateCrowdUser(crowdUserDto);
            } else {
                log.info("Create User(display-name: " + displayName + ")");
                crowdUserDto = CrowdUserDto.builder()
                        .name(userDto.getUserId())
                        .password(new CrowdUserDto.Password())
                        .firstName(userDto.getName())
                        .lastName(userDto.getName())
                        .email(userDto.getEmail())
                        .displayName(displayName)
                        .active(true)
                        .build();
                updateOrCreateRes = crowdUserService.createCrowdUser(crowdUserDto);
            }

            if (!updateOrCreateRes.getStatusCode().isError()) {
                log.info("updated_user and crowd_username_custom table upsert row. User(corp_code=" + userDto.getCorpCode() + ", user_id=" + userDto.getUserId() + ")");
                UpdatedUserDto updatedUserDto = UpdatedUserDto.builder()
                        .corpCode(userDto.getCorpCode())
                        .userId(userDto.getUserId())
                        .name(userDto.getName())
                        .deptName(userDto.getDeptName())
                        .posName(userDto.getPosName())
                        .firstName(crowdUserDto.getFirstName())
                        .lastName(crowdUserDto.getLastName())
                        .emailAddress(crowdUserDto.getEmail())
                        .displayName(displayName)
                        .build();
                CrowdUsernameCustomDto crowdUsernameCustomDto = userDto.isChkYn() ?
                        null : userDto.getUserCustomName() == null ?
                        null : CrowdUsernameCustomDto.builder()
                            .corpCode(userDto.getCorpCode())
                            .userId(userDto.getUserId())
                            .userCustomName(displayName)
                            .build();
                try {
                    crowdUserService.saveUser(updatedUserDto, crowdUsernameCustomDto);
                } catch (PersistenceException | DataAccessException e) {
                    log.error("Can not upsert updated_user or crowd_username_custom table.");
                    log.error("exception reason : " + e.getMessage());
                }
            } else {
                log.error("Can not create or update User(userId=" + userDto.getUserId() + ")");
                log.error("status code : " + updateOrCreateRes.getStatusCodeValue() + ", reason phrase : " + updateOrCreateRes.getStatusCode().getReasonPhrase());
            }
        });
        log.info("Crowd display name update schedule end");
    }
}
