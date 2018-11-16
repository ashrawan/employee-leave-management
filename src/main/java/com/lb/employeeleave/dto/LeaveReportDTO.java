package com.lb.employeeleave.dto;

public class LeaveReportDTO {

    private int count;

    private String leaveType;

    private int month;

    private String status;

    public LeaveReportDTO(int count, String leaveType, int month, String status) {
        this.count = count;
        this.leaveType = leaveType;
        this.month = month;
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
