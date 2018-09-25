package com.lb.employeeleave.service;

import com.lb.employeeleave.entity.LeaveType;

import java.util.List;

public interface LeaveTypeService {

    List<LeaveType> getAllLeaveTypes();

    LeaveType getLeaveTypeById(Long id);

    // only admin
    LeaveType createLeaveType(LeaveType leaveType);

    // only admin
    LeaveType updateLeaveType(LeaveType leaveType);

}
