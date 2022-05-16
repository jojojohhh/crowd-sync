package com.osci.crowdsync.entity;

import com.osci.crowdsync.dto.CrowdUsernameCustomDto;
import lombok.*;

import javax.persistence.*;

/**
 * REQ_UPDATED_USER 테이블 Entity Class
 */
@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(UserId.class)
@Table(name = "CROWD_USERNAME_CUSTOM")
public class CrowdUsernameCustom {
    @Id
    @Column(name = "CORP_CODE")
    private String corpCode;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_CUSTOM_NAME")
    private String userCustomName;

    @OneToOne(mappedBy = "crowdUsernameCustom")
    private SysUser sysUser;

    public CrowdUsernameCustom(CrowdUsernameCustomDto crowdUsernameCustomDto) {
        this.corpCode = crowdUsernameCustomDto.getCorpCode();
        this.userId = crowdUsernameCustomDto.getUserId();
        this.userCustomName = crowdUsernameCustomDto.getUserCustomName();
    }
}
