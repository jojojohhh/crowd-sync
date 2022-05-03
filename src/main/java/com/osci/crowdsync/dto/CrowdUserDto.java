package com.osci.crowdsync.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrowdUserDto {

    private String name;

    @JsonProperty("first-name")
    private String firstName;

    @JsonProperty("last-name")
    private String lastName;

    @JsonProperty("display-name")
    private String displayName;
    private String email;
}
