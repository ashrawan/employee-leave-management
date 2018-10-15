package com.lb.employeeleave.mapper;

import com.lb.employeeleave.dto.EmployeeDTO;
import com.lb.employeeleave.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDTO convertToDto(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setUsername(employee.getUsername());
        employeeDTO.setPassword(employee.getPassword());
        employeeDTO.setRole(employee.getRole());
        employeeDTO.setFullName(employee.getFullName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setCreatedDateTime(employee.getCreatedDateTime());
        if(employee.getEmployeeSupervisor() != null) {
            employeeDTO.setEmployeeSupervisor(convertToDto(employee.getEmployeeSupervisor()));
        }
        employeeDTO.setStatus(employee.getStatus());
        return employeeDTO;
    }

    public static Employee convertToEntity(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setUsername(employeeDTO.getUsername());
        employee.setPassword(employeeDTO.getPassword());
        employee.setRole(employeeDTO.getRole());
        employee.setFullName(employeeDTO.getFullName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setCreatedDateTime(employeeDTO.getCreatedDateTime());
        if(employeeDTO.getEmployeeSupervisor() != null) {
            employee.setEmployeeSupervisor(convertToEntity(employeeDTO.getEmployeeSupervisor()));
        }
        employee.setStatus(employeeDTO.getStatus());
        return employee;
    }
}
