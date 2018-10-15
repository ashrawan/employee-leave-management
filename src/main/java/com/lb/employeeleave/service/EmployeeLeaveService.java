package com.lb.employeeleave.service;

import com.lb.employeeleave.dto.EmployeeLeaveDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface EmployeeLeaveService {

    @PreAuthorize ("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    Page<EmployeeLeaveDTO> getAllEmployeeLeaves(Pageable pageable);

    @PreAuthorize ("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    EmployeeLeaveDTO getEmployeeLeaveById(Long id);

    // only employee can create their own leave request
    @PreAuthorize("#employeeLeaveDTO.employee.id == authentication.principal.id ")
    EmployeeLeaveDTO createEmployeeLeave(EmployeeLeaveDTO employeeLeaveDTO);

    // only employee if leave request is still pending
    @PreAuthorize("#employeeLeaveDTO.employee.id == authentication.principal.id ")
    EmployeeLeaveDTO updateEmployeeLeave(EmployeeLeaveDTO employeeLeaveDTO);

    // only admin and EmployeeSupervisor
    @PreAuthorize ("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    EmployeeLeaveDTO approveEmployeeLeave(EmployeeLeaveDTO employeeLeaveDTO);

    // only employee on their own pending leave request
    @PreAuthorize("#id == authentication.principal.id ")
    EmployeeLeaveDTO ChangeEmployeeLeaveStatus(Long id);
}
