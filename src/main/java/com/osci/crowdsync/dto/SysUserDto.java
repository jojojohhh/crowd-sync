package com.osci.crowdsync.dto;

import com.osci.crowdsync.entity.SysUser;
import lombok.*;

import java.sql.Timestamp;
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysUserDto {
    private String corpCode;
    private String userId;
    private String name;
    private String nameEng;
    private String deptCode;
    private String deptName;
    private String posCode;
    private String posName;
    private String email;
    private String languageKind;
    private String useYn;
    private int failLogCnt;
    private String regCorp;
    private String regUser;
    private Timestamp regDate;
    private String uptCorp;
    private Timestamp uptDate;
    private String dutyCode;
    private String dutyName;
    private UpdatedUserDto updatedUserDto;
    private CrowdUsernameCustomDto crowdUsernameCustomDto;

    public SysUserDto(SysUser sysUser) {
        this.corpCode = sysUser.getCorpCode();
        this.userId = sysUser.getUserId();
        this.name = sysUser.getName();
        this.nameEng = sysUser.getNameEng();
        this.deptCode = sysUser.getDeptCode();
        this.deptName = sysUser.getDeptName();
        this.posCode = sysUser.getPosCode();
        this.posName = sysUser.getPosName();
        this.email = sysUser.getEmail();
        this.languageKind = sysUser.getLanguageKind();
        this.useYn = sysUser.getUseYn();
        this.failLogCnt = sysUser.getFailLogCnt();
        this.regCorp = sysUser.getUptCorp();
        this.regUser = sysUser.getRegUser();
        this.regDate = sysUser.getRegDate();
        this.uptCorp = sysUser.getUptCorp();
        this.uptDate = sysUser.getUptDate();
        this.dutyCode = sysUser.getDutyCode();
        this.dutyName = sysUser.getDutyName();
        this.updatedUserDto = sysUser.getUpdatedUser() == null ? new UpdatedUserDto() : new UpdatedUserDto(sysUser.getUpdatedUser());
        this.crowdUsernameCustomDto = sysUser.getCrowdUsernameCustom() == null ? new CrowdUsernameCustomDto() : new CrowdUsernameCustomDto(sysUser.getCrowdUsernameCustom());
    }
}
