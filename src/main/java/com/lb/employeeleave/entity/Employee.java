package com.lb.employeeleave.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.util.converter.LocalDateTimeStringConverter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
//    @Enumerated(EnumType.STRING)
    private String role;

    @Column
    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @Column
    private int status;

//    @JsonIgnoreProperties(value = "employeeSupervisor")
    @OneToOne
    @JoinColumn(name="supervisor_employee")
    private Employee employeeSupervisor;

//    @JsonIgnore
//    @OneToMany(mappedBy = "employee")
//    private List<EmployeeLeave> employeeLeaves = new ArrayList<>();
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "reviewedByEmployee")
//    private List<EmployeeLeave> reviewedEmployeeLeaves = new ArrayList<>();


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

//    public List<EmployeeLeave> getEmployeeLeaves() {
//        return employeeLeaves;
//    }
//
//    public void setEmployeeLeaves(List<EmployeeLeave> employeeLeaves) {
//        this.employeeLeaves = employeeLeaves;
//    }
//
//    public List<EmployeeLeave> getReviewedEmployeeLeaves() {
//        return reviewedEmployeeLeaves;
//    }
//
//    public void setReviewedEmployeeLeaves(List<EmployeeLeave> reviewedEmployeeLeaves) {
//        this.reviewedEmployeeLeaves = reviewedEmployeeLeaves;
//    }
}
