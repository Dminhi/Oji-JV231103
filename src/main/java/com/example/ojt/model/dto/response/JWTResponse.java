package com.example.ojt.model.dto.response;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class JWTResponse {
    private final String type = "Bearer";
    private String accessToken ;
    private String email;
    private String avatar;
    private String name;
    private String roleName;
    private Integer accountId;
    private Collection<? extends GrantedAuthority> roleSet;
    private Integer status;
}

