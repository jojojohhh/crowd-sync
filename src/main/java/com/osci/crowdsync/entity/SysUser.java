package com.osci.crowdsync.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToOne(mappedBy = "sysUser")
    private UpdatedUser updatedUser;
}
