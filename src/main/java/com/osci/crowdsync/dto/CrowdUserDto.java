package com.osci.crowdsync.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CrowdUserDto {

    private String name;

    private Password password;

    @JsonProperty("first-name")
    private String firstName;

    @JsonProperty("last-name")
    private String lastName;

    @JsonProperty("display-name")
    private String displayName;

    private String email;

    @Getter
    public static class Password {
        private String value;

        public Password() {
            this.value = "secret";
        }
    }
}
