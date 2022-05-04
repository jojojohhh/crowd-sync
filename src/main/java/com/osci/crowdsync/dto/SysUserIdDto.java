package com.osci.crowdsync.dto;

import com.osci.crowdsync.entity.SysUserId;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysUserIdDto {
    private String corpCode;
    private String userId;
}
