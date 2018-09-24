package com.lb.employeeleave.serviceImpl;

import com.lb.employeeleave.constant.ExceptionConstants;
import com.lb.employeeleave.entity.Employee;
import com.lb.employeeleave.exceptions.DataNotFoundException;
import com.lb.employeeleave.repository.EmployeeRepository;
import com.lb.employeeleave.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {

        return employeeRepository.findById(id).orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND));
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {

        Optional<Employee> returnedEmployee = employeeRepository.findById(employee.getId());
        if (!returnedEmployee.isPresent()) {
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND);
        }
        Employee employee1 = returnedEmployee.get();
        if (employee1.getId() == employee.getEmployeeSupervisor().getId()){
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_SUPERVISOR_MISMATCH);
        }
        employee1.setFullName(employee.getFullName());
        employee1.setEmail(employee1.getEmail());
        employee1.setEmployeeSupervisor(employee.getEmployeeSupervisor());

       return employeeRepository.save(employee1);
    }
}
