package com.lb.employeeleave.serviceImpl;

import com.lb.employeeleave.constant.ExceptionConstants;
import com.lb.employeeleave.dto.EmployeeDTO;
import com.lb.employeeleave.entity.Employee;
import com.lb.employeeleave.exceptions.DataConflictException;
import com.lb.employeeleave.exceptions.DataNotFoundException;
import com.lb.employeeleave.mapper.EmployeeMapper;
import com.lb.employeeleave.repository.EmployeeRepository;
import com.lb.employeeleave.security.JwtUserDetails;
import com.lb.employeeleave.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
     * @return List of Employee
     */
    @Override
    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        Page<EmployeeDTO> employeeDTOPage = employeePage.map(employee -> EmployeeMapper.convertToDto(employee));
        return employeeDTOPage;
    }

    /**
     * Get single Employee Record
     * @param id
     * @return If present Employee else throws Exception
     */
    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(!employee.isPresent()){
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND);
        }
        return EmployeeMapper.convertToDto(employee.get());
    }

    @Override
    public EmployeeDTO retrieveAuthenticatedEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long authenticatedEmployeeId = ((JwtUserDetails) authentication.getPrincipal()).getId();

        Optional<Employee> employee = employeeRepository.findById(authenticatedEmployeeId);
        if(!employee.isPresent()){
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND);
        }
        return EmployeeMapper.convertToDto(employee.get());
    }

    /**
     * Create New Employee
     * If EmployeeSupervisor id is sent but id doesn't exist in database then throws Exception
     * @param employeeDTO
     * @return saved Employee
     */
    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {

        //  EmployeeSupervisor id is sent but id doesn't exist in database
        if(employeeDTO.getEmployeeSupervisor()!= null &&
                employeeDTO.getEmployeeSupervisor().getId()!= null &&
                !employeeRepository.findById(employeeDTO.getEmployeeSupervisor().getId()).isPresent()){
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_SUPERVISOR_MISMATCH);
        }
        employeeDTO.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        Employee employee = employeeRepository.save(EmployeeMapper.convertToEntity(employeeDTO));
        return EmployeeMapper.convertToDto(employee);
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

        Optional<Employee> returnedEmployee = employeeRepository.findById(employeeDTO.getId());
        // Employee must be present in database
        if (!returnedEmployee.isPresent()) {
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND);
        }
        Employee employee = returnedEmployee.get();
        // Employee cannot be their own Supervisor and EmployeeSupervisor must be present in database
        if ((employeeDTO.getEmployeeSupervisor()!= null &&
                employeeDTO.getEmployeeSupervisor().getId()!= null) &&
                (employee.getId() == employeeDTO.getEmployeeSupervisor().getId()
                        || !employeeRepository.findById(employeeDTO.getEmployeeSupervisor().getId()).isPresent())){
            throw new DataConflictException(ExceptionConstants.EMPLOYEE_SUPERVISOR_MISMATCH);
        }
        employee.setFullName(employeeDTO.getFullName());
        employee.setUsername(employeeDTO.getUsername());
        employee.setEmail(employeeDTO.getEmail());
        employee.setEmployeeSupervisor(EmployeeMapper.convertToEntity(employeeDTO.getEmployeeSupervisor()));

       return EmployeeMapper.convertToDto(employeeRepository.save(employee));
    }

    @Override
    public List<EmployeeDTO> getAllEmployeeUnderSupervision(Long id) {
        Optional<Employee> returnedEmployee = employeeRepository.findById(id);
        // Employee must be present in database
        if (!returnedEmployee.isPresent()) {
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND);
        }
        List<Employee> employeeList = employeeRepository.findAllByEmployeeSupervisor(returnedEmployee.get());
        return employeeList
                .stream()
                .map(employee -> EmployeeMapper.convertToDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public Page<EmployeeDTO> getAllEmployeesByName(Pageable pageable, String fullName) {
        Page<Employee> employeePage = employeeRepository.findByFullNameStartingWithIgnoreCase(pageable, fullName);
        Page<EmployeeDTO> employeeDTOPage = employeePage.map(employee -> EmployeeMapper.convertToDto(employee));
        return employeeDTOPage;
    }
}
