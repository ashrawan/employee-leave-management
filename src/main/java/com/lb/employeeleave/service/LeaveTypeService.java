package com.lb.employeeleave.service;

import com.lb.employeeleave.entity.LeaveType;

import java.util.List;

public interface LeaveTypeService {

    List<LeaveType> getAllLeaveTypes();

    LeaveType getLeaveTypeById(Long id);

    LeaveType createLeaveType(LeaveType leaveType);

    LeaveType updateLeaveType(LeaveType leaveType);

}
