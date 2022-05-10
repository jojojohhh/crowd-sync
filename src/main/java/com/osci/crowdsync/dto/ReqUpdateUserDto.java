package com.osci.crowdsync.dto;


import com.osci.crowdsync.entity.ReqUpdateUser;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReqUpdateUserDto {

    private String corpCode;
    private String userId;
    private String displayName;
    private String deptCode;
    private Character updated;

    public ReqUpdateUserDto(ReqUpdateUser reqUpdateUser) {
        this.corpCode = reqUpdateUser.getCorpCode();
        this.userId = reqUpdateUser.getUserId();
        this.displayName = reqUpdateUser.getDisplayName();
        this.deptCode = reqUpdateUser.getDeptCode();
        this.updated = reqUpdateUser.getUpdated();
    }
}
