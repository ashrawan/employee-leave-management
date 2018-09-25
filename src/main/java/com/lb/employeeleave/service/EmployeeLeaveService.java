package com.lb.employeeleave.service;

import com.lb.employeeleave.entity.EmployeeLeave;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeLeaveService {

    List<EmployeeLeave> getAllEmployeeLeaves(Pageable pageable);

    EmployeeLeave getEmployeeLeaveById(Long id);

    // only employee can create their own leave request
    EmployeeLeave createEmployeeLeave(EmployeeLeave employeeLeave);

    // only employee if leave request is still pending
    EmployeeLeave updateEmployeeLeave(EmployeeLeave employeeLeave);

    // only admin and EmployeeSupervisor
    EmployeeLeave approveEmployeeLeave(EmployeeLeave employeeLeave);

    // only employee on their own pending leave request
    boolean deletePendingEmployeeLeave(Long id);
}
