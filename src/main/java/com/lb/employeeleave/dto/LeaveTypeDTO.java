package com.lb.employeeleave.dto;

import com.lb.employeeleave.constant.enums.LeaveTypeStatus;

public class LeaveTypeDTO {

    private Long leaveTypeId;

    private String typeName;

    private LeaveTypeStatus status;

    public Long getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(Long leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public LeaveTypeStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveTypeStatus status) {
        this.status = status;
    }
}
