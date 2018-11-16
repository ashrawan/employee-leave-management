package com.lb.employeeleave.entity;

import com.lb.employeeleave.util.enums.LeaveTypeStatus;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaveType leaveType = (LeaveType) o;
        return Objects.equals(leaveTypeId, leaveType.leaveTypeId) &&
                Objects.equals(typeName, leaveType.typeName) &&
                status == leaveType.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(leaveTypeId, typeName, status);
    }
}
