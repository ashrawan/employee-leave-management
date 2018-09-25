package com.lb.employeeleave.serviceImpl;

import com.lb.employeeleave.constant.ExceptionConstants;
import com.lb.employeeleave.entity.Employee;
import com.lb.employeeleave.exceptions.DataConflictException;
import com.lb.employeeleave.exceptions.DataNotFoundException;
import com.lb.employeeleave.repository.EmployeeRepository;
import com.lb.employeeleave.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Get all Employees Record
     * @return List of Employee
     */
    @Override
    public Page<Employee> getAllEmployees(Pageable pageable) {

        return employeeRepository.findAll(pageable);
    }

    /**
     * Get single Employee Record
     * @param id
     * @return If present Employee else throws Exception
     */
    @Override
    public Employee getEmployeeById(Long id) {

        return employeeRepository.findById(id).orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND));
    }

    /**
     * Create New Employee
     * If EmployeeSupervisor id is sent but id doesn't exist in database then throws Exception
     * @param employee
     * @return saved Employee
     */
    @Override
    public Employee createEmployee(Employee employee) {

        //  EmployeeSupervisor id is sent but id doesn't exist in database
        if(employee.getEmployeeSupervisor()!= null && !employeeRepository.findById(employee.getEmployeeSupervisor().getId()).isPresent()){
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_SUPERVISOR_MISMATCH);
        }
        return employeeRepository.save(employee);
    }

    /**
     * Update Employee
     * Employee must be present in database else throws Exception
     * Employee cannot be their own Supervisor and EmployeeSupervisor id must be present in database else throws Exception
     * Can only update Employee FullName, Email and EmployeeSupervisor
     *
     * @param employee
     * @return updated Employee
     */
    @Override
    public Employee updateEmployee(Employee employee) {

        Optional<Employee> returnedEmployee = employeeRepository.findById(employee.getId());
        // Employee must be present in database
        if (!returnedEmployee.isPresent()) {
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND);
        }
        Employee employee1 = returnedEmployee.get();
        // Employee cannot be their own Supervisor and EmployeeSupervisor must be present in database
        if (employee1.getId() == employee.getEmployeeSupervisor().getId() || !employeeRepository.findById(employee.getEmployeeSupervisor().getId()).isPresent()){
            throw new DataConflictException(ExceptionConstants.EMPLOYEE_SUPERVISOR_MISMATCH);
        }
        employee1.setFullName(employee.getFullName());
        employee1.setEmail(employee1.getEmail());
        employee1.setEmployeeSupervisor(employee.getEmployeeSupervisor());

       return employeeRepository.save(employee1);
    }

    @Override
    public List<Employee> getAllEmployeeUnderSupervision(Long id) {
        Optional<Employee> returnedEmployee = employeeRepository.findById(id);
        // Employee must be present in database
        if (!returnedEmployee.isPresent()) {
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND);
        }
        return employeeRepository.findAllByEmployeeSupervisor(returnedEmployee.get());
    }
}
