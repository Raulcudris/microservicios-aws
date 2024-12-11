package com.makiia.auth_service.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String username;
    private String password;
}