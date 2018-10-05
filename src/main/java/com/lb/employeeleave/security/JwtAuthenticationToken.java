package com.lb.employeeleave.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken{

    private String token;

    public JwtAuthenticationToken(String token) {
        super(null, null);
        this.token = token;
    }

    @JsonIgnore
    @Override
    public Object getCredentials() {
        return null;
    }

    @JsonIgnore
    @Override
    public Object getPrincipal() {
        return null;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}