package com.lb.employeeleave.dto;

import com.lb.employeeleave.constant.enums.Status;

public class LeaveTypeDTO {

    private Long leaveTypeId;

    private String typeName;

    private Status status;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
