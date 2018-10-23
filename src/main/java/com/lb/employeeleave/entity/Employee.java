package com.lb.employeeleave.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="employee")
public class Employee {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fullName;

    @Column
    private String email;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private String role;

    @Column
    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @Column
    private int status;

    @OneToOne
    @JoinColumn(name="supervisor_employee")
    private Employee employeeSupervisor;

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

    public Employee getEmployeeSupervisor() {
        return employeeSupervisor;
    }

    public void setEmployeeSupervisor(Employee employeeSupervisor) {
        this.employeeSupervisor = employeeSupervisor;
    }

}
