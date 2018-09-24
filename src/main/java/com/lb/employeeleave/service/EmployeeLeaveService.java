package com.lb.employeeleave.service;

import com.lb.employeeleave.entity.EmployeeLeave;

import java.util.List;

public interface EmployeeLeaveService {

    List<EmployeeLeave> getAllEmployeeLeaves();

    EmployeeLeave getEmployeeLeaveById(Long id);

    EmployeeLeave createEmployeeLeave(EmployeeLeave employeeLeave);

    EmployeeLeave updateEmployeeLeave(EmployeeLeave employeeLeave);

    // access to only admin and supervisor
    EmployeeLeave approveEmployeeLeave(EmployeeLeave employeeLeave);

    // employee can only delete their own pending leave request
    boolean deletePendingEmployeeLeave(Long id);
}
