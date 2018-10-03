package com.lb.employeeleave.service;

import com.lb.employeeleave.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface EmployeeService {

    @PreAuthorize ("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    Page<Employee> getAllEmployees(Pageable pageable);

    Employee getActiveUser(String username, int status);

    @PreAuthorize ("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    Employee getEmployeeById(Long id);

    // only allowed to admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Employee createEmployee(Employee employee);

    // only allowed to admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Employee updateEmployee(Employee employee);

    // only allowed to admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<Employee> getAllEmployeeUnderSupervision(Long id);

    @PreAuthorize ("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    Page<Employee> getAllEmployeesByName(Pageable pageable, String fullName);
}
