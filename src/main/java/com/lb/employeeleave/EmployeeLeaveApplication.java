package com.lb.employeeleave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeLeaveApplication {

    public static void main(String[] args) {
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        System.out.println( encoder.encode("admin"));
        SpringApplication.run(EmployeeLeaveApplication.class, args);
    }
}
