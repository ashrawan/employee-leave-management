package com.lb.employeeleave.service;

import com.lb.employeeleave.mapper.StatusMapper;
import com.lb.employeeleave.util.ExceptionConstants;
import com.lb.employeeleave.util.enums.EmployeeStatus;
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
                .map(employee -> EmployeeMapper.mapToDTOWithSupervisor(employee));
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
        return EmployeeMapper.mapToDTOWithSupervisor(employee);
    }

    @Override
    public EmployeeDTO retrieveAuthenticatedEmployee() {

        long authenticatedEmployeeId = ExtractUserAuthentication.getCurrentUser().getId();
        Employee employee = employeeRepository.findById(authenticatedEmployeeId)
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND));
        return EmployeeMapper.mapToDTOWithSupervisor(employee);
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
        if (employeeDTO.getSupervisor() != null &&
                employeeDTO.getSupervisor().getEmployeeId() != null &&
                !employeeRepository.findById(employeeDTO.getSupervisor().getEmployeeId()).isPresent()) {
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_SUPERVISOR_MISMATCH);
        }
        if (employeeDTO.getUsername() == null || employeeRepository.findByUsername(employeeDTO.getUsername()) != null) {
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_USERNAME_NOT_VALID);
        }

        employeeDTO.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        employeeDTO.setRole("ROLE_USER");
        employeeDTO.setPhoneNumber(employeeDTO.getPhoneNumber());
        employeeDTO.setStatus(employeeDTO.getStatus());
        Employee employee = employeeRepository.save(EmployeeMapper.mapToEntityWithSupervisor(employeeDTO));
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

        Employee returnedEmployee = employeeRepository.findById(employeeDTO.getEmployeeId())
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND));

        // Employee cannot be their own Supervisor and EmployeeSupervisor must be present in database
        if ((employeeDTO.getSupervisor() != null &&
                employeeDTO.getSupervisor().getEmployeeId() != null) &&
                (returnedEmployee.getEmployeeId() == employeeDTO.getSupervisor().getEmployeeId()
                        || !employeeRepository.findById(employeeDTO.getSupervisor().getEmployeeId()).isPresent())) {
            throw new DataConflictException(ExceptionConstants.EMPLOYEE_SUPERVISOR_MISMATCH);
        }
        returnedEmployee.setFirstName(employeeDTO.getFirstName());
        returnedEmployee.setMiddleName(employeeDTO.getMiddleName());
        returnedEmployee.setLastName(employeeDTO.getLastName());
        returnedEmployee.setEmail(employeeDTO.getEmail());
        returnedEmployee.setPhoneNumber(employeeDTO.getPhoneNumber());
        if (employeeDTO.getSupervisor() != null) {
            returnedEmployee.setSupervisor(EmployeeMapper.mapToEntity(employeeDTO.getSupervisor()));
        } else {
            returnedEmployee.setSupervisor(null);
        }
        return EmployeeMapper.mapToDto(employeeRepository.save(returnedEmployee));
    }

    @Override
    public EmployeeDTO updatePassword(String oldPassword, String newPassword) {

        long authenticatedEmployeeId = ExtractUserAuthentication.getCurrentUser().getId();
        Employee employee = employeeRepository.findById(authenticatedEmployeeId)
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND));
        if (!passwordEncoder.matches(oldPassword, employee.getPassword())) {
            throw new DataNotFoundException(ExceptionConstants.OLD_PASSWORD_DOESNT_MATCH);
        }
        employee.setPassword(passwordEncoder.encode(newPassword));
        return EmployeeMapper.mapToDto(employeeRepository.save(employee));
    }

    @Override
    public List<EmployeeDTO> getAllEmployeeUnderSupervision(Long id) {

        Employee returnedEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND));

        return employeeRepository.findAllBySupervisor(returnedEmployee)
                .stream()
                .map(employee -> EmployeeMapper.mapToDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public Page<EmployeeDTO> getAllEmployeesByName(Pageable pageable, String fullName) {

        return employeeRepository.findByFirstNameContainingOrMiddleNameContainingOrLastNameContaining(pageable, fullName, fullName, fullName)
                .map(employee -> EmployeeMapper.mapToDTOWithSupervisor(employee));
    }
}
