package com.osci.crowdsync.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@ToString
@Getter
public class InactiveUserDto {

    private String corpCode;

    private String userId;

    private String name;

    private String deptName;

    private String posName;

    private String firstName;

    private String lastName;

    private String email;

    private String displayName;

    private String useYn;

    @QueryProjection
    public InactiveUserDto(String corpCode, String userId, String name, String deptName, String posName, String firstName, String lastName, String email, String displayName, String useYn) {
        this.corpCode = corpCode;
        this.userId = userId;
        this.name = name;
        this.deptName = deptName;
        this.posName = posName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.displayName = displayName;
        this.useYn = useYn;
    }
}
