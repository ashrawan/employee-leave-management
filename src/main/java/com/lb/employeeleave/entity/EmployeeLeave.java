package com.lb.employeeleave.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="employee_leave")
public class EmployeeLeave {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name="leave_type")
    private LeaveType leaveType;

    @Column(columnDefinition="TEXT")
    private String leaveReason;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate leaveDateFrom;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate leaveDateTo;

    @Column
    private int isApproved;

    @Column(columnDefinition="TEXT")
    private String deniedReason;

    @Column
    private int status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column
    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne
    @JoinColumn(name="reviewed_by_employee")
    private Employee reviewedByEmployee;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public LocalDate getLeaveDateFrom() {
        return leaveDateFrom;
    }

    public void setLeaveDateFrom(LocalDate leaveDateFrom) {
        this.leaveDateFrom = leaveDateFrom;
    }

    public LocalDate getLeaveDateTo() {
        return leaveDateTo;
    }

    public void setLeaveDateTo(LocalDate leaveDateTo) {
        this.leaveDateTo = leaveDateTo;
    }

    public int getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(int isApproved) {
        this.isApproved = isApproved;
    }

    public String getDeniedReason() {
        return deniedReason;
    }

    public void setDeniedReason(String deniedReason) {
        this.deniedReason = deniedReason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Employee getReviewedByEmployee() {
        return reviewedByEmployee;
    }

    public void setReviewedByEmployee(Employee reviewedByEmployee) {
        this.reviewedByEmployee = reviewedByEmployee;
    }
}
