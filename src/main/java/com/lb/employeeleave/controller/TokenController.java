package com.lb.employeeleave.controller;

import com.lb.employeeleave.entity.Employee;
import com.lb.employeeleave.exceptions.DataConflictException;
import com.lb.employeeleave.exceptions.UnauthorizedRequest;
import com.lb.employeeleave.security.JwtAuthenticationToken;
import com.lb.employeeleave.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.lb.employeeleave.security.SecurityConstant.TOKEN_PREFIX;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class TokenController {

    @Autowired
    private JwtGenerator jwtGenerator;

    @PostMapping("/login")
    public ResponseEntity<?> generate(@RequestBody Employee employee) {

        if (StringUtils.isEmpty(employee.getUsername()) || StringUtils.isEmpty(employee.getPassword())) {
            throw new DataConflictException("Invalid Login request, Username Password not present");
        }

        String token = jwtGenerator.generate(employee);

        if (token == null) {
            throw new UnauthorizedRequest("Invalid Username Password");
        }

        JwtAuthenticationToken tokens = new JwtAuthenticationToken(TOKEN_PREFIX + token);
        return new ResponseEntity<>(tokens, HttpStatus.OK);

    }
}
