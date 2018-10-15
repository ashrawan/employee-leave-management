package com.lb.employeeleave.dto;

import java.util.ArrayList;
import java.util.List;

public class LeaveTypeDTO {

    private Long id;

    private String typeName;

    private int status;

    private List<EmployeeLeaveDTO> leaveList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<EmployeeLeaveDTO> getLeaveList() {
        return leaveList;
    }

    public void setLeaveList(List<EmployeeLeaveDTO> leaveList) {
        this.leaveList = leaveList;
    }
}
