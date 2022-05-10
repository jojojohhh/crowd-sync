package com.osci.crowdsync.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(UserId.class)
@Table(name = "REQ_UPDATE_USER")
public class ReqUpdateUser {
    @Id
    @Column(name = "CORP_CODE")
    private String corpCode;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "DEPT_CODE")
    private String deptCode;

    @Column(name = "UPDATED")
    private Character updated;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "CORP_CODE"),
            @JoinColumn(name = "USER_ID")
    })
    private SysUser sysUser;
}
