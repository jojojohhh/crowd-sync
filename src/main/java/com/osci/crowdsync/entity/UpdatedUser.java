package com.osci.crowdsync.entity;

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

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "CORP_CODE"),
            @JoinColumn(name = "USER_ID")
    })
    private SysUser sysUser;

    public UpdatedUser(UpdatedUserDto updatedUserDto) {
        this.corpCode = updatedUserDto.getCorpCode();
        this.userId = updatedUserDto.getUserId();
        this.displayName = updatedUserDto.getDisplayName();
    }
}
