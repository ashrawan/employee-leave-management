package com.lb.employeeleave.dto;

import com.lb.employeeleave.constant.enums.LeaveStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LeaveDTO {

    private Long leaveId;

    private EmployeeDTO employeeDTO;

    private LeaveTypeDTO leaveTypeDTO;

    private String leaveReason;

    private LocalDate fromDate;

    private LocalDate toDate;

    private boolean approved;

    private String deniedReason;

    private LeaveStatus status;

    private LocalDateTime createdAt;

    private EmployeeDTO reviewedBy;

    public Long getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Long leaveId) {
        this.leaveId = leaveId;
    }

    public EmployeeDTO getEmployeeDTO() {
        return employeeDTO;
    }

    public void setEmployeeDTO(EmployeeDTO employeeDTO) {
        this.employeeDTO = employeeDTO;
    }

    public LeaveTypeDTO getLeaveTypeDTO() {
        return leaveTypeDTO;
    }

    public void setLeaveTypeDTO(LeaveTypeDTO leaveTypeDTO) {
        this.leaveTypeDTO = leaveTypeDTO;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getDeniedReason() {
        return deniedReason;
    }

    public void setDeniedReason(String deniedReason) {
        this.deniedReason = deniedReason;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public EmployeeDTO getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(EmployeeDTO reviewedBy) {
        this.reviewedBy = reviewedBy;
    }
}
