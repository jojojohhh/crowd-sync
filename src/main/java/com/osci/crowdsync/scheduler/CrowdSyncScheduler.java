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
        log.info("Crowd display name update scheduler start (default format : name/pos_name/dept_name)");

        crowdUserService.getAll().forEach(sysUser -> {
            boolean isCustom = false;
            UpdatedUserDto updatedUser = sysUser.getUpdatedUserDto();
            CrowdUsernameCustomDto usernameCustom = sysUser.getCrowdUsernameCustomDto();

            if (updatedUser.getUserId() == null) {
                isCustom = usernameCustom.getUserCustomName() != null;
            } else {
                if (usernameCustom.getUserCustomName() != null) isCustom = !updatedUser.getDisplayName().equals(usernameCustom.getUserCustomName());
            }

            String displayName;
            if (isCustom)   displayName = usernameCustom.getUserCustomName();
            else    displayName = sysUser.getName() + " / " + sysUser.getPosName() + " / " + sysUser.getDeptName();

            ResponseEntity<CrowdUserDto> res = crowdUserService.getCrowdUser(sysUser.getUserId());
            CrowdUserDto resBody = res.getBody();

            log.info("User(userId=" + sysUser.getUserId() + ")");
            ResponseEntity<CrowdUserDto> updateOrCreateRes;
            CrowdUserDto crowdUserDto;
            if (!res.getStatusCode().isError()) {
                log.info("Update the user display name. (before: " + resBody.getDisplayName() + " -> after: " + displayName + ")");
                crowdUserDto = CrowdUserDto.builder()
                        .name(resBody.getName())
                        .password(new CrowdUserDto.Password())
                        .firstName(sysUser.getName())
                        .lastName(sysUser.getName())
                        .email(resBody.getEmail())
                        .displayName(displayName)
                        .build();
                updateOrCreateRes = crowdUserService.updateCrowdUser(crowdUserDto);
            } else {
                log.info("Create User(display-name: " + displayName + ")");
                crowdUserDto = CrowdUserDto.builder()
                        .name(sysUser.getUserId())
                        .password(new CrowdUserDto.Password())
                        .firstName(sysUser.getName())
                        .lastName(sysUser.getName())
                        .email(sysUser.getEmail())
                        .displayName(displayName)
                        .build();
                updateOrCreateRes = crowdUserService.createCrowdUser(crowdUserDto);
            }

            if (!updateOrCreateRes.getStatusCode().isError()) {
                log.info("updated_user and crowd_username_custom table upsert row. User(corp_code=" + sysUser.getCorpCode() + ", user_id=" + sysUser.getUserId() + ")");
                UpdatedUserDto updatedUserDto = UpdatedUserDto.builder()
                        .corpCode(sysUser.getCorpCode())
                        .userId(sysUser.getUserId())
                        .name(sysUser.getName())
                        .deptName(sysUser.getDeptName())
                        .posName(sysUser.getPosName())
                        .firstName(crowdUserDto.getFirstName())
                        .lastName(crowdUserDto.getLastName())
                        .emailAddress(crowdUserDto.getEmail())
                        .displayName(displayName)
                        .build();
                CrowdUsernameCustomDto crowdUsernameCustomDto = usernameCustom.getCorpCode() == null ?
                        null : CrowdUsernameCustomDto.builder()
                        .corpCode(usernameCustom.getCorpCode())
                        .userId(usernameCustom.getUserId())
                        .userCustomName(displayName)
                        .build();
                try {
                    crowdUserService.saveUser(updatedUserDto, crowdUsernameCustomDto);
                } catch (PersistenceException | DataAccessException e) {
                    log.error("Can not upsert updated_user or crowd_username_custom table.");
                    log.error("exception reason : " + e.getMessage());
                }
            } else {
                log.error("Can not create or update User(userId=" + sysUser.getUserId() + ")");
                log.error("status code : " + updateOrCreateRes.getStatusCodeValue() + ", reason phrase : " + updateOrCreateRes.getStatusCode().getReasonPhrase());
            }
        });
        log.info("Crowd display name update schedule end");
    }
}
