package com.osci.crowdsync.dto;

import com.osci.crowdsync.entity.UpdatedUser;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdatedUserDto {
    private String corpCode;
    private String userId;

    private String name;

    private String deptName;

    private String posName;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String displayName;

    public UpdatedUserDto(UpdatedUser updatedUser) {
        this.corpCode = updatedUser.getCorpCode();
        this.userId = updatedUser.getUserId();
        this.name = updatedUser.getName();
        this.deptName = updatedUser.getDeptName();
        this.posName = updatedUser.getPosName();
        this.firstName = updatedUser.getFirstName();
        this.lastName = updatedUser.getLastName();
        this.emailAddress = updatedUser.getEmailAddress();
        this.displayName = updatedUser.getDisplayName();
    }
}
