package com.osci.crowdsync.dto;

import com.osci.crowdsync.entity.SysUser;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class SysUserDto {

    private String corpCode;

    private String userId;

    private String name;

    private String deptName;

    private String posName;

    private String email;

    @QueryProjection
    public SysUserDto(String corpCode, String userId, String name, String deptName, String posName, String email) {
        this.corpCode = corpCode;
        this.userId = userId;
        this.name = name;
        this.deptName = deptName;
        this.posName = posName;
        this.email = email;
    }
}
