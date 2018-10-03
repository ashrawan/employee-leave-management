package com.lb.employeeleave.security;

import com.lb.employeeleave.entity.Employee;
import com.lb.employeeleave.service.EmployeeService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class JwtGenerator {

	@Autowired
	private EmployeeService employeeService;

	public String generate(Employee employee) {

		Employee activeUser = null;

		activeUser = employeeService.getActiveUser(employee.getUsername(),1);

		if (activeUser.getUsername() != null && activeUser.getPassword() != null
				&& activeUser.getUsername().equals(employee.getUsername())
				&& new BCryptPasswordEncoder().matches(employee.getPassword(), activeUser.getPassword())) {

			Claims claims = Jwts.claims().setSubject(employee.getUsername());
			claims.put("id", String.valueOf(activeUser.getId()));
			claims.put("fullName", String.valueOf(activeUser.getFullName()));
			claims.put("role", activeUser.getRole());

			ZonedDateTime expireTime = ZonedDateTime.now(ZoneOffset.UTC).plus(SecurityConstant.EXPIRATION_TIME,ChronoUnit.MILLIS);
			
			return Jwts.builder()
					.setClaims(claims)
					.setExpiration(Date.from(expireTime.toInstant()))
					.signWith(SignatureAlgorithm.HS512,SecurityConstant.SECRET).compact();
		}
		return null;

	}
}