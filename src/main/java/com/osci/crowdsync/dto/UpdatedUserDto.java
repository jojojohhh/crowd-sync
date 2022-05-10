package com.osci.crowdsync.dto;

import com.osci.crowdsync.entity.UpdatedUser;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedUserDto {
    private String corpCode;
    private String userId;
    private String displayName;

    public UpdatedUserDto(UpdatedUser updatedUser) {
        this.corpCode = updatedUser.getCorpCode();
        this.userId = updatedUser.getUserId();
        this.displayName = updatedUser.getDisplayName();
    }
}
