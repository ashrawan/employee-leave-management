package com.lb.employeeleave.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="leave_type")
public class LeaveType {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String type_name;

    @Column
    private int status;

    @JsonIgnore
    @OneToMany(mappedBy = "leaveType")
    private List<EmployeeLeave> leaveList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<EmployeeLeave> getLeaveList() {
        return leaveList;
    }

    public void setLeaveList(List<EmployeeLeave> leaveList) {
        this.leaveList = leaveList;
    }
}
