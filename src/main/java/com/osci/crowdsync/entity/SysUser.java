package com.osci.crowdsync.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * SYS_USER 테이블 Entity Class
 */
@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(UserId.class)
@Table(name = "SYS_USER")
public class SysUser {

    @Id
    @Column(name = "CORP_CODE")
    private String corpCode;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "PASSWORD")
    private Byte[] password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NAME_ENG")
    private String nameEng;

    @Column(name = "DEPT_CODE")
    private String deptCode;

    @Column(name = "DEPT_NAME")
    private String deptName;

    @Column(name = "POS_CODE")
    private String posCode;

    @Column(name = "POS_NAME")
    private String posName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "LANGUAGE_KIND")
    private String languageKind;

    @Column(name = "USE_YN")
    private String useYn;

    @Column(name = "FAIL_LOG_CNT")
    private int failLogCnt;

    @Column(name = "REG_CORP")
    private String regCorp;

    @Column(name = "REG_USER")
    private String regUser;

    @Column(name = "REG_DATE")
    private Timestamp regDate;

    @Column(name = "UPT_CORP")
    private String uptCorp;

    @Column(name = "UPT_DATE")
    private Timestamp uptDate;

    @Column(name = "DUTY_CODE")
    private String dutyCode;

    @Column(name = "DUTY_NAME")
    private String dutyName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "CORP_CODE"),
            @JoinColumn(name = "USER_ID")
    })
    private UpdatedUser updatedUser;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "CORP_CODE"),
            @JoinColumn(name = "USER_ID")
    })
    private CrowdUsernameCustom crowdUsernameCustom;
}
