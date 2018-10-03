package com.lb.employeeleave.service;

import com.lb.employeeleave.entity.EmployeeLeave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface EmployeeLeaveService {

    @PreAuthorize ("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    Page<EmployeeLeave> getAllEmployeeLeaves(Pageable pageable);

    @PreAuthorize ("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    EmployeeLeave getEmployeeLeaveById(Long id);

    // only employee can create their own leave request
    @PreAuthorize("#employeeLeave.employee.id == authentication.principal.id ")
    EmployeeLeave createEmployeeLeave(EmployeeLeave employeeLeave);

    // only employee if leave request is still pending
    @PreAuthorize("#employeeLeave.employee.id == authentication.principal.id ")
    EmployeeLeave updateEmployeeLeave(EmployeeLeave employeeLeave);

    // only admin and EmployeeSupervisor
    @PreAuthorize ("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    EmployeeLeave approveEmployeeLeave(EmployeeLeave employeeLeave);

    // only employee on their own pending leave request
    @PreAuthorize("#id == authentication.principal.id ")
    boolean deletePendingEmployeeLeave(Long id);
}
