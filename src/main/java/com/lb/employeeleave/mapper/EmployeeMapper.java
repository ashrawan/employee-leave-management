package com.lb.employeeleave.mapper;

import com.lb.employeeleave.dto.EmployeeDTO;
import com.lb.employeeleave.entity.Employee;

public class EmployeeMapper {

    static Boolean employeeSupervisorMapped = false;

    public static EmployeeDTO mapToDto(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setUsername(employee.getUsername());
        employeeDTO.setPassword(employee.getPassword());
        employeeDTO.setRole(employee.getRole());
        employeeDTO.setFullName(employee.getFullName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setCreatedDateTime(employee.getCreatedDateTime());
        employeeDTO.setStatus(employee.getStatus());
        if(employee.getEmployeeSupervisor() != null && !employeeSupervisorMapped) {
            employeeSupervisorMapped = true;
            employeeDTO.setEmployeeSupervisor(mapToDto(employee.getEmployeeSupervisor()));
        }
        employeeSupervisorMapped = false;
        return employeeDTO;
    }

    public static Employee mapToEntity(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setUsername(employeeDTO.getUsername());
        employee.setPassword(employeeDTO.getPassword());
        employee.setRole(employeeDTO.getRole());
        employee.setFullName(employeeDTO.getFullName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setCreatedDateTime(employeeDTO.getCreatedDateTime());
        employee.setStatus(employeeDTO.getStatus());
        if(employeeDTO.getEmployeeSupervisor() != null && !employeeSupervisorMapped) {
            employeeSupervisorMapped = true;
            employee.setEmployeeSupervisor(mapToEntity(employeeDTO.getEmployeeSupervisor()));
        }
        employeeSupervisorMapped = false;
        return employee;
    }
}
