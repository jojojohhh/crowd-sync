package com.osci.crowdsync.scheduler;

import com.osci.crowdsync.dto.CrowdUserDto;
import com.osci.crowdsync.entity.CrowdUsernameCustom;
import com.osci.crowdsync.entity.UpdatedUser;
import com.osci.crowdsync.service.CrowdUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceException;
import java.util.ArrayList;


@Log4j2
@RequiredArgsConstructor
@Component
public class CrowdSyncScheduler {

    private final CrowdUserService crowdUserService;

    @Value("${stream.thread-pool.size}")
    private String threadPoolSize;

    /**
     * Crowd display name 을 업데이트 및 비활성 사용자에 대해 Crowd 계정을 삭제하는 스케줄러, cron expression 으로 동작한다.
     * 기본 Display Name : 이름 직책 / 부서
     * 커스텀 Display Name : CROWD_USERNAME_CUSTOM 테이블의 USER_CUSTOM_NAME column 참조
     */
    @Scheduled(cron = "${scheduler.cron}")
    public void crowdSyncJob() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", threadPoolSize);

        log.info("Crowd display name update scheduler start (default format : name pos_name / dept_name)");
        ArrayList<UpdatedUser> updatedUsers = new ArrayList<>();
        ArrayList<CrowdUsernameCustom> usernameCustoms = new ArrayList<>();
        crowdUserService.getUserReqUpdate().stream().parallel().forEach(userDto -> {
            log.info(userDto.toString());

            boolean isCustom = false;
            if (userDto.getUpdatedUserDisplayName() == null) {
                isCustom = userDto.getUserCustomName() != null;
            } else {
                if (userDto.getUserCustomName() != null)    isCustom = !userDto.getUpdatedUserDisplayName().equals(userDto.getUserCustomName());
            }

            String displayName = isCustom ? userDto.getUserCustomName() : userDto.getName() + " " + userDto.getPosName() + " / " + userDto.getDeptName();

            log.info("User(userId=" + userDto.getUserId() + ") displayName(displayName=" + displayName + ", isCustom=" + isCustom +  ")");

            ResponseEntity<CrowdUserDto> res = crowdUserService.getCrowdUserByWebClient(userDto.getUserId());
            CrowdUserDto resBody = res.getBody();

            log.info(userDto.toString());
            ResponseEntity<CrowdUserDto> updateOrCreateRes = null;
            CrowdUserDto crowdUserDto = null;
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
                updateOrCreateRes = crowdUserService.updateCrowdUserByWebClient(crowdUserDto);
            } else if (res.getStatusCodeValue() == HttpStatus.NOT_FOUND.value()) {
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
                updateOrCreateRes = crowdUserService.createCrowdUserByWebClient(crowdUserDto);
            }

            if (updateOrCreateRes != null) {
                if (!updateOrCreateRes.getStatusCode().isError()) {
                    updatedUsers.add(UpdatedUser.builder()
                            .corpCode(userDto.getCorpCode())
                            .userId(userDto.getUserId())
                            .name(userDto.getName())
                            .deptName(userDto.getDeptName())
                            .posName(userDto.getPosName())
                            .firstName(crowdUserDto.getFirstName())
                            .lastName(crowdUserDto.getLastName())
                            .emailAddress(crowdUserDto.getEmail())
                            .displayName(displayName)
                            .active(userDto.getUseYn())
                            .build());
                    if (!(userDto.getUserCustomName() == null || userDto.getUpdatedUserDisplayName() == null))
                        usernameCustoms.add(CrowdUsernameCustom.builder()
                                .corpCode(userDto.getCorpCode())
                                .userId(userDto.getUserId())
                                .userCustomName(displayName)
                                .chkYn(userDto.getChkYn())
                                .build());
                } else {
                    log.error("Can not create or update User(userId=" + userDto.getUserId() + ")");
                    log.error("status code : " + updateOrCreateRes.getStatusCodeValue() + ", reason phrase : " + updateOrCreateRes.getStatusCode().getReasonPhrase());
                }
            } else {
                log.error(userDto.toString());
                log.error(res.getStatusCode().getReasonPhrase());
            }
        });

        try {
            crowdUserService.saveUser(updatedUsers, usernameCustoms);
        } catch (PersistenceException | DataAccessException e) {
            log.error("Can not upsert updated_user or crowd_username_custom table.");
            log.error("exception reason : " + e.getMessage());
        }

        log.info("Crowd display name update schedule end");
        log.info("Inactive User delete crowd account schedule start");
        updatedUsers.clear();
        crowdUserService.getInactiveUsers().stream().parallel().forEach(inactiveUserDto -> {
            log.info("User(userId=" + inactiveUserDto.getUserId() + ") delete");
            ResponseEntity<CrowdUserDto> getRes = crowdUserService.getCrowdUserByWebClient(inactiveUserDto.getUserId());
            if (!getRes.getStatusCode().isError()) {
                ResponseEntity<CrowdUserDto> delRes = crowdUserService.deleteCrowdUserByWebClient(inactiveUserDto.getUserId());
                if (!delRes.getStatusCode().isError()) {
                    updatedUsers.add(UpdatedUser.builder()
                            .corpCode(inactiveUserDto.getCorpCode())
                            .userId(inactiveUserDto.getUserId())
                            .name(inactiveUserDto.getName())
                            .deptName(inactiveUserDto.getDeptName())
                            .posName(inactiveUserDto.getPosName())
                            .firstName(inactiveUserDto.getFirstName())
                            .lastName(inactiveUserDto.getLastName())
                            .emailAddress(inactiveUserDto.getEmail())
                            .displayName(inactiveUserDto.getDisplayName())
                            .active(inactiveUserDto.getUseYn())
                            .build());
                } else {
                    log.error("Can not delete User(userId=" + inactiveUserDto.getUserId() + ")");
                }
            } else {
                log.warn("User(userId=" + inactiveUserDto.getUserId() + ") can't find in crowd");
            }
        });
        try {
            crowdUserService.saveUser(updatedUsers, null);
        } catch (PersistenceException | DataAccessException e) {
            log.error("Can not upsert updated_user table.");
            log.error("exception reason : " + e.getMessage());
        }
        log.info("Inactive User delete crowd account schedule end");
    }
}
