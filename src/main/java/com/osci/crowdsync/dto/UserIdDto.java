package com.osci.crowdsync.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserIdDto {
    private String corpCode;
    private String userId;
}