package com.lb.employeeleave.service;

import com.lb.employeeleave.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    Employee createEmployee(Employee employee);

    // update only allowed to admin
    Employee updateEmployee(Employee employee);
}
