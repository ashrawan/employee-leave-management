package com.lb.employeeleave.service;

import com.lb.employeeleave.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    Page<Employee> getAllEmployees(Pageable pageable);

    Employee getEmployeeById(Long id);

    // only allowed to admin
    Employee createEmployee(Employee employee);

    // only allowed to admin
    Employee updateEmployee(Employee employee);
}
