package com.max.auth.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthVO {
    @JsonProperty("token")
    private String token;
}
