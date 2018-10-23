package com.lb.employeeleave.mapper;

import com.lb.employeeleave.dto.EmployeeDTO;
import com.lb.employeeleave.entity.Employee;

public class EmployeeMapper {

    static Boolean supervisorMapped = false;

    public static EmployeeDTO mapToDto(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setUsername(employee.getUsername());
//        employeeDTO.setPassword(employee.getPassword());
        employeeDTO.setRole(employee.getRole());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setMiddleName(employee.getMiddleName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setCreatedAt(employee.getCreatedAt());
        employeeDTO.setStatus(employee.getStatus());
        if (employee.getSupervisor() != null && !supervisorMapped) {
            supervisorMapped = true;
            employeeDTO.setSupervisor(mapToDto(employee.getSupervisor()));
        }
        supervisorMapped = false;
        return employeeDTO;
    }

    public static Employee mapToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDTO.getEmployeeId());
        employee.setUsername(employeeDTO.getUsername());
        employee.setPassword(employeeDTO.getPassword());
        employee.setRole(employeeDTO.getRole());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setMiddleName(employeeDTO.getMiddleName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setCreatedAt(employeeDTO.getCreatedAt());
        employee.setStatus(employeeDTO.getStatus());
        if (employeeDTO.getSupervisor() != null && !supervisorMapped) {
            supervisorMapped = true;
            employee.setSupervisor(mapToEntity(employeeDTO.getSupervisor()));
        }
        supervisorMapped = false;
        return employee;
    }
}
