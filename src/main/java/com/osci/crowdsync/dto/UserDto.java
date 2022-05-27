package com.osci.crowdsync.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.print.DocFlavor;

@Data
@ToString
@Getter
public class UserDto {

    private String corpCode;

    private String userId;

    private String name;

    private String deptName;

    private String posName;

    private String email;

    private String updatedUserDisplayName;

    private String userCustomName;

    private String useYn;

    private String chkYn;


    @QueryProjection
    public UserDto(String corpCode, String userId, String name, String deptName, String posName, String email, String updatedUserDisplayName, String userCustomName, String useYn, String chkYn) {
        this.corpCode = corpCode;
        this.userId = userId;
        this.name = name;
        this.deptName = deptName;
        this.posName = posName;
        this.email = email;
        this.updatedUserDisplayName = updatedUserDisplayName;
        this.userCustomName = userCustomName;
        this.useYn = useYn;
        this.chkYn = chkYn;
    }
}
