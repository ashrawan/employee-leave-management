package com.lb.employeeleave.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmployeeLeaveDTO {

    private Long id;

    private EmployeeDTO employee;

    private LeaveTypeDTO leaveType;

    private String leaveReason;

    private LocalDate leaveDateFrom;

    private LocalDate leaveDateTo;

    private int isApproved;

    private String deniedReason;

    private int status;

    private LocalDateTime createdDateTime;

    private EmployeeDTO reviewedByEmployee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public LeaveTypeDTO getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveTypeDTO leaveType) {
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

    public EmployeeDTO getReviewedByEmployee() {
        return reviewedByEmployee;
    }

    public void setReviewedByEmployee(EmployeeDTO reviewedByEmployee) {
        this.reviewedByEmployee = reviewedByEmployee;
    }
}
