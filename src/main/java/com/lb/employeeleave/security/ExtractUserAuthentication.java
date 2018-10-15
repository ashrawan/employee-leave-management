package com.lb.employeeleave.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ExtractUserAuthentication {

    public static JwtUserDetails getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (JwtUserDetails) authentication.getPrincipal();
    }

}
