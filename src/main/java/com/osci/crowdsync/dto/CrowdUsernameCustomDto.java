package com.osci.crowdsync.dto;


import com.osci.crowdsync.entity.CrowdUsernameCustom;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CrowdUsernameCustomDto {

    private String corpCode;
    private String userId;
    private String userCustomName;
    private String chkYn;

    public CrowdUsernameCustomDto(CrowdUsernameCustom crowdUsernameCustom) {
        this.corpCode = crowdUsernameCustom.getCorpCode();
        this.userId = crowdUsernameCustom.getUserId();
        this.userCustomName = crowdUsernameCustom.getUserCustomName();
        this.chkYn = crowdUsernameCustom.getChkYn();
    }
}
