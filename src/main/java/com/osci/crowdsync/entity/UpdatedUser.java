package com.osci.crowdsync.entity;

import com.osci.crowdsync.dto.SysUserDto;
import com.osci.crowdsync.dto.UpdatedUserDto;
import lombok.*;

import javax.persistence.*;

/**
 * UPDATED_USER 테이블 Entity Class
 */
@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(UserId.class)
@Table(name = "UPDATED_USER")
public class UpdatedUser {

    @Id
    @Column(name = "CORP_CODE")
    private String corpCode;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DEPT_NAME")
    private String deptName;

    @Column(name = "POS_NAME")
    private String posName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String emailAddress;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "ACTIVE")
    private String active;

    @OneToOne(mappedBy = "updatedUser")
    private SysUser sysUser;

    public UpdatedUser(UpdatedUserDto updatedUserDto) {
        this.corpCode = updatedUserDto.getCorpCode();
        this.userId = updatedUserDto.getUserId();
        this.name = updatedUserDto.getName();
        this.deptName = updatedUserDto.getDeptName();
        this.posName = updatedUserDto.getPosName();
        this.firstName = updatedUserDto.getFirstName();
        this.lastName = updatedUserDto.getLastName();
        this.emailAddress = updatedUserDto.getEmailAddress();
        this.displayName = updatedUserDto.getDisplayName();
        this.active = updatedUserDto.getActive();
    }
}
