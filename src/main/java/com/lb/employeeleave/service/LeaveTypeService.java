package com.lb.employeeleave.service;

import com.lb.employeeleave.entity.LeaveType;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface LeaveTypeService {

    @PreAuthorize ("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    List<LeaveType> getAllLeaveTypes();

    @PreAuthorize ("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    LeaveType getLeaveTypeById(Long id);

    // only admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    LeaveType createLeaveType(LeaveType leaveType);

    // only admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    LeaveType updateLeaveType(LeaveType leaveType);

}
