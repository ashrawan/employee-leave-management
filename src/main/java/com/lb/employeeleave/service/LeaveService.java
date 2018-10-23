package com.lb.employeeleave.service;

import com.lb.employeeleave.dto.LeaveDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface LeaveService {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    Page<LeaveDTO> getAllEmployeeLeaves(Pageable pageable);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    LeaveDTO getEmployeeLeaveById(Long id);

    // only employee can create their own leave request
    @PreAuthorize("#employeeLeaveDTO.employee.employeeId == authentication.principal.id ")
    LeaveDTO createEmployeeLeave(LeaveDTO employeeLeaveDTO);

    // only employee if leave request is still pending
    @PreAuthorize("#employeeLeaveDTO.employee.employeeId == authentication.principal.id ")
    LeaveDTO updateEmployeeLeave(LeaveDTO employeeLeaveDTO);

    // only admin and EmployeeSupervisor
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    LeaveDTO approveEmployeeLeave(LeaveDTO employeeLeaveDTO);

    // only employee on their own pending leave request
    @PreAuthorize("#id == authentication.principal.id ")
    LeaveDTO ChangeEmployeeLeaveStatus(Long id);
}
