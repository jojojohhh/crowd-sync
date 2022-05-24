package com.osci.crowdsync.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDto {

    private String userId;

    private String name;

    private String deptName;

    private String posName;

    private String email;

    private boolean useYn;

    private String updatedUserDeptName;

    private String updatedUserPosName;

    private String updatedUserDisplayName;

    private String userCustomName;


    @QueryProjection
    public UserDto(String userId, String name, String deptName, String posName, String email, String useYn, String updatedUserDeptName, String updatedUserPosName, String updatedUserDisplayName, String userCustomName) {
        this.userId = userId;
        this.name = name;
        this.deptName = deptName;
        this.posName = posName;
        this.email = email;
        this.useYn = useYn.equals("Y");
        this.updatedUserDeptName = updatedUserDeptName;
        this.updatedUserPosName = updatedUserPosName;
        this.updatedUserDisplayName = updatedUserDisplayName;
        this.userCustomName = userCustomName;
    }

}
