package com.osci.crowdsync.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserId.class)
@ToString
public class UpdatedUser {

    @Id
    @Column(name = "CORP_CODE")
    private String corpCode;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "CORP_CODE"),
            @JoinColumn(name = "USER_ID")
    })
    private SysUser sysUser;
}
