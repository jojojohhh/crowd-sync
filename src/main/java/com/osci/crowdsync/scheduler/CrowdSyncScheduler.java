package com.osci.crowdsync.scheduler;

import com.osci.crowdsync.dto.CrowdUserDto;
import com.osci.crowdsync.dto.ReqUpdateUserDto;
import com.osci.crowdsync.dto.SysUserDto;
import com.osci.crowdsync.dto.UpdatedUserDto;
import com.osci.crowdsync.service.CrowdUserService;
import com.osci.crowdsync.service.ReqUpdateUserService;
import com.osci.crowdsync.service.UpdatedUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceException;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Component
public class CrowdSyncScheduler {

    private final CrowdUserService crowdUserService;
    private final UpdatedUserService updatedUserService;
    private final ReqUpdateUserService reqUpdateUserService;

    /**
     * Crowd display name 을 업데이트 하기 위한 스케줄러 cron expression 으로 동작한다.
     * 기본 Display Name : 이름/직책/부서
     * 커스텀 Display Name : REQ_UPDATE_USER 테이블의 display_name column 참조
     */
    @Scheduled(cron = "${scheduler.cron}")
    public void crowdSyncJob() {
        log.info("Crowd display name update scheduler start (default format : name/pos_name/dept_name)");
        List<SysUserDto> users = crowdUserService.findAllNotUpdated();
        users.forEach(user -> {
            ResponseEntity<CrowdUserDto> res = crowdUserService.getCrowdUser(user.getUserId());
            CrowdUserDto resBody = res.getBody();
            String displayName = user.getName() + "/" + user.getPosName() + "/" + user.getDeptName();

            log.info("User(userId=" + user.getUserId() + ")");
            ResponseEntity<CrowdUserDto> updateOrCreateRes;
            if (!res.getStatusCode().isError()) {
                log.info("Update the user display name. (before: " + resBody.getDisplayName() + " -> after: " + displayName + ")");
                updateOrCreateRes = crowdUserService.updateCrowdUser(CrowdUserDto.builder()
                        .name(resBody.getName())
                        .password(new CrowdUserDto.Password())
                        .firstName(resBody.getFirstName())
                        .lastName(resBody.getLastName())
                        .email(resBody.getEmail())
                        .displayName(displayName)
                        .build());
            } else {
                log.info("Create User(display-name: " + displayName + ")");
                updateOrCreateRes = crowdUserService.createCrowdUser(CrowdUserDto.builder()
                        .name(user.getUserId())
                        .password(new CrowdUserDto.Password())
                        .firstName(user.getName())
                        .lastName(user.getName())
                        .email(user.getEmail())
                        .displayName(displayName)
                        .build());
            }

            if (!updateOrCreateRes.getStatusCode().isError()) {
                log.info("updated_user table insert row User(corp_code=" + user.getCorpCode() + ", user_id=" + user.getUserId() + ")");
                updatedUserService.save(UpdatedUserDto.builder()
                        .corpCode(user.getCorpCode())
                        .userId(user.getUserId())
                        .displayName(displayName)
                        .build());
            } else {
                log.error("Can not create or update User(userId=" + user.getUserId() + ")");
                log.error("status code : " + updateOrCreateRes.getStatusCodeValue() + ", reason phrase : " + updateOrCreateRes.getStatusCode().getReasonPhrase());
            }
        });

        log.info("Custom display name update");
        reqUpdateUserService.findAllReqUpdateUsers().forEach(user -> {
            ResponseEntity<CrowdUserDto> res = crowdUserService.getCrowdUser(user.getUserId());
            CrowdUserDto resBody = res.getBody();
            String displayName = user.getDisplayName();

            log.info("User(userId=" + user.getUserId() + ")");
            if (!res.getStatusCode().isError()) {
                log.info("Update the user display name. (before: " + resBody.getDisplayName() + " -> after: " + displayName + ")");
                CrowdUserDto crowdUserDto = CrowdUserDto.builder()
                        .name(resBody.getName())
                        .password(new CrowdUserDto.Password())
                        .firstName(resBody.getFirstName())
                        .lastName(resBody.getLastName())
                        .email(resBody.getEmail())
                        .displayName(displayName)
                        .build();

                ResponseEntity<CrowdUserDto> updateRes = crowdUserService.updateCrowdUser(crowdUserDto);
                if (!updateRes.getStatusCode().isError()) {
                    try {
                        reqUpdateUserService.save(ReqUpdateUserDto.builder()
                                .corpCode(user.getCorpCode())
                                .userId(user.getUserId())
                                .displayName(displayName)
                                .deptCode(user.getDeptCode())
                                .updated('1')
                                .build());

                        updatedUserService.save(UpdatedUserDto.builder()
                                .corpCode(user.getCorpCode())
                                .userId(user.getUserId())
                                .displayName(displayName)
                                .build());
                    } catch (DataAccessException | PersistenceException e) {
                        log.error(e.getMessage());
                    }
                } else {
                    log.error("Can not update User(userId=" + user.getUserId() + ").");
                    log.error("status code : " + updateRes.getStatusCodeValue() + ", reason phrase : " + updateRes.getStatusCode().getReasonPhrase());
                }
            }
        });
        log.info("Crowd display name update schedule end");
    }
}
