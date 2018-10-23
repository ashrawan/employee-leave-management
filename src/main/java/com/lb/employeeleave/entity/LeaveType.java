package com.lb.employeeleave.entity;

import com.lb.employeeleave.constant.enums.Status;

import javax.persistence.*;

@Entity
@Table(name="leave_type")
public class LeaveType {

    @Id
    @Column(name = "leave_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveTypeId;

    @Column(name = "type_name", unique = true, nullable = false)
    private String typeName;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

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
