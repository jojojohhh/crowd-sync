package com.osci.crowdsync.dto;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrowdUserDto {

    private String username;
    private String firstName;
    private String lastName;
    private String displayName;
    private String emailAddress;
}
