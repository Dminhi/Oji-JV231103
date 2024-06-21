package com.example.ojt.model.dto.response;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class JWTResponse {
    private final String type = "Bearer";
    private String accessToken ;
    private String email;
    private Long accountId;
    private Collection<? extends GrantedAuthority> roleSet;
    private Integer status;
}

