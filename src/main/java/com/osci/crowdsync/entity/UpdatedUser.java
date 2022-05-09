package com.osci.crowdsync.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserId.class)
@Table(name = "UPDATED_USER")
public class UpdatedUser {

    @Id
    @Column(name = "CORP_CODE")
    private String corpCode;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "CORP_CODE"),
            @JoinColumn(name = "USER_ID")
    })
    private SysUser sysUser;
}
