package com.lb.employeeleave.security;

import com.lb.employeeleave.entity.Employee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    public Employee validate(String token) {

        Employee employee = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(SecurityConstant.SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            employee = new Employee();
            employee.setUsername(body.getSubject());
            employee.setId(Long.parseLong((String) body.get("id")));
            employee.setRole((String) body.get("role"));
            
           
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return employee;
    }
}