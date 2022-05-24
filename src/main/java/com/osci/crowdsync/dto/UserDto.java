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

    private boolean useYn;

    private String updatedUserDeptName;

    private String updatedUserPosName;

    private String updatedUserDisplayName;

    private String userCustomName;

    private boolean chkYn;


    @QueryProjection
    public UserDto(String corpCode, String userId, String name, String deptName, String posName, String email, String useYn, String updatedUserDeptName, String updatedUserPosName, String updatedUserDisplayName, String userCustomName, String chkYn) {
        this.corpCode = corpCode;
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
        this.chkYn = chkYn.equals("Y");
    }

}
