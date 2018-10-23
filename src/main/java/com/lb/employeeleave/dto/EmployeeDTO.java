package com.lb.employeeleave.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class EmployeeDTO {

    private Long id;

    private String fullName;

    private String email;

    private String username;

    @JsonIgnore
    private String password;

    private String role;

    private LocalDateTime createdDateTime;

    private int status;

    private EmployeeDTO employeeSupervisor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public EmployeeDTO getEmployeeSupervisor() {
        return employeeSupervisor;
    }

    public void setEmployeeSupervisor(EmployeeDTO employeeSupervisor) {
        this.employeeSupervisor = employeeSupervisor;
    }

}
