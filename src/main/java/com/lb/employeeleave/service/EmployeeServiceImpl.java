package com.lb.employeeleave.service;

import com.lb.employeeleave.constant.ExceptionConstants;
import com.lb.employeeleave.dto.EmployeeDTO;
import com.lb.employeeleave.entity.Employee;
import com.lb.employeeleave.exceptions.DataConflictException;
import com.lb.employeeleave.exceptions.DataNotFoundException;
import com.lb.employeeleave.mapper.EmployeeMapper;
import com.lb.employeeleave.repository.EmployeeRepository;
import com.lb.employeeleave.security.ExtractUserAuthentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(final EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Get all Employees Record
     *
     * @return List of Employee
     */
    @Override
    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {

        return employeeRepository.findAll(pageable)
                .map(employee -> EmployeeMapper.mapToDto(employee));
    }

    /**
     * Get single Employee Record
     *
     * @param id
     * @return If present Employee else throws Exception
     */
    @Override
    public EmployeeDTO getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND));
        return EmployeeMapper.mapToDto(employee);
    }

    @Override
    public EmployeeDTO retrieveAuthenticatedEmployee() {

        long authenticatedEmployeeId = ExtractUserAuthentication.getCurrentUser().getId();
        Employee employee = employeeRepository.findById(authenticatedEmployeeId)
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND));
        return EmployeeMapper.mapToDto(employee);
    }

    /**
     * Create New Employee
     * If EmployeeSupervisor id is sent but id doesn't exist in database then throws Exception
     *
     * @param employeeDTO
     * @return saved Employee
     */
    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {

        //  EmployeeSupervisor id is sent but id doesn't exist in database
        if (employeeDTO.getEmployeeSupervisor() != null &&
                employeeDTO.getEmployeeSupervisor().getId() != null &&
                !employeeRepository.findById(employeeDTO.getEmployeeSupervisor().getId()).isPresent()) {
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_SUPERVISOR_MISMATCH);
        }
        employeeDTO.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        Employee employee = employeeRepository.save(EmployeeMapper.mapToEntity(employeeDTO));
        return EmployeeMapper.mapToDto(employee);
    }

    /**
     * Update Employee
     * Employee must be present in database else throws Exception
     * Employee cannot be their own Supervisor and EmployeeSupervisor id must be present in database else throws Exception
     * Can only update Employee FullName, Email and EmployeeSupervisor
     *
     * @param employeeDTO
     * @return updated Employee
     */
    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {

        Employee returnedEmployee = employeeRepository.findById(employeeDTO.getId())
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND));

        // Employee cannot be their own Supervisor and EmployeeSupervisor must be present in database
        if ((employeeDTO.getEmployeeSupervisor() != null &&
                employeeDTO.getEmployeeSupervisor().getId() != null) &&
                (returnedEmployee.getId() == employeeDTO.getEmployeeSupervisor().getId()
                        || !employeeRepository.findById(employeeDTO.getEmployeeSupervisor().getId()).isPresent())) {
            throw new DataConflictException(ExceptionConstants.EMPLOYEE_SUPERVISOR_MISMATCH);
        }
        returnedEmployee.setFullName(employeeDTO.getFullName());
        returnedEmployee.setUsername(employeeDTO.getUsername());
        returnedEmployee.setEmail(employeeDTO.getEmail());
        returnedEmployee.setEmployeeSupervisor(EmployeeMapper.mapToEntity(employeeDTO.getEmployeeSupervisor()));
        return EmployeeMapper.mapToDto(employeeRepository.save(returnedEmployee));
    }

    @Override
    public List<EmployeeDTO> getAllEmployeeUnderSupervision(Long id) {

        Employee returnedEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND));

        return employeeRepository.findAllByEmployeeSupervisor(returnedEmployee)
                .stream()
                .map(employee -> EmployeeMapper.mapToDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public Page<EmployeeDTO> getAllEmployeesByName(Pageable pageable, String fullName) {

        return employeeRepository.findByFullNameStartingWithIgnoreCase(pageable, fullName)
                .map(employee -> EmployeeMapper.mapToDto(employee));
    }
}
