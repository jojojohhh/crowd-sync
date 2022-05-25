package com.osci.crowdsync.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

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


    @QueryProjection
    public UserDto(String corpCode, String userId, String name, String deptName, String posName, String email, String updatedUserDisplayName, String userCustomName) {
        this.corpCode = corpCode;
        this.userId = userId;
        this.name = name;
        this.deptName = deptName;
        this.posName = posName;
        this.email = email;
        this.updatedUserDisplayName = updatedUserDisplayName;
        this.userCustomName = userCustomName;
    }

}
