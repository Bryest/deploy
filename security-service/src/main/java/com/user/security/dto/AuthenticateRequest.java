package com.user.security.dto;

import lombok.Data;

@Data
public class AuthenticateRequest {
    private String username;
    private String password;
}
