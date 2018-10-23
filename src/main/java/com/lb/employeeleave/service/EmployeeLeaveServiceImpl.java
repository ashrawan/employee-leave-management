package com.lb.employeeleave.service;

import com.lb.employeeleave.constant.ExceptionConstants;
import com.lb.employeeleave.dto.EmployeeLeaveDTO;
import com.lb.employeeleave.entity.Employee;
import com.lb.employeeleave.entity.EmployeeLeave;
import com.lb.employeeleave.exceptions.DataConflictException;
import com.lb.employeeleave.exceptions.DataNotFoundException;
import com.lb.employeeleave.exceptions.UnauthorizedRequest;
import com.lb.employeeleave.mapper.EmployeeLeaveMapper;
import com.lb.employeeleave.repository.EmployeeLeaveRepository;
import com.lb.employeeleave.security.ExtractUserAuthentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeLeaveServiceImpl implements EmployeeLeaveService {

    private final EmployeeLeaveRepository employeeLeaveRepository;

    public EmployeeLeaveServiceImpl(final EmployeeLeaveRepository employeeLeaveRepository){
        this.employeeLeaveRepository = employeeLeaveRepository;
    }

    /**
     * Get all EmployeeLeave Record
     * @return List of EmployeeLeave
     */
    @Override
    public Page<EmployeeLeaveDTO> getAllEmployeeLeaves(Pageable pageable) {

        return employeeLeaveRepository.findAll(pageable)
                .map(employeeLeave -> EmployeeLeaveMapper.mapToDto(employeeLeave));
    }

    /**
     * Get single EmployeeLeave Record
     * @param id
     * @return If present EmployeeLeave else throw Exception
     */
    @Override
    public EmployeeLeaveDTO getEmployeeLeaveById(Long id) {

        EmployeeLeave employeeLeave = employeeLeaveRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));
        return EmployeeLeaveMapper.mapToDto(employeeLeave);
    }

    /**
     * Create New EmployeeLeave Request
     * @param employeeLeaveDTO
     * @return saved EmployeeLeave
     */
    @Override
    public EmployeeLeaveDTO createEmployeeLeave(EmployeeLeaveDTO employeeLeaveDTO) {

        EmployeeLeave employeeLeave = employeeLeaveRepository.save(EmployeeLeaveMapper.mapToEntity(employeeLeaveDTO));
        return EmployeeLeaveMapper.mapToDto(employeeLeave);
    }

    /**
     * Update EmployeeLeave
     * EmployeeLeave Record must be present in database else throws Exception
     * EmployeeLeave status must be in pending else throws Exception
     * @param employeeLeaveDTO
     * @return updated EmployeeLeave
     */
    @Override
    public EmployeeLeaveDTO updateEmployeeLeave(EmployeeLeaveDTO employeeLeaveDTO) {

        EmployeeLeave returnedEmployeeLeave = employeeLeaveRepository.findById(employeeLeaveDTO.getId())
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));

        // EmployeeLeave status must be in pending
        if(returnedEmployeeLeave.getReviewedByEmployee() != null){
            throw new DataConflictException(ExceptionConstants.EMPLOYEE_LEAVE_ACTION_ALREADY_TAKEN);
        }
        returnedEmployeeLeave.setLeaveDateFrom(employeeLeaveDTO.getLeaveDateFrom());
        returnedEmployeeLeave.setLeaveDateTo(employeeLeaveDTO.getLeaveDateTo());
        returnedEmployeeLeave.setLeaveReason(employeeLeaveDTO.getLeaveReason());
        return EmployeeLeaveMapper.mapToDto(employeeLeaveRepository.save(returnedEmployeeLeave));
    }

    /**
     * Approved EmployeeLeave request
     * EmployeeLeave Record must be present in database else throws Exception
     *
     * @param employeeLeaveDTO
     * @return approved EmployeeLeave
     */
    @Override
    public EmployeeLeaveDTO approveEmployeeLeave(EmployeeLeaveDTO employeeLeaveDTO) {

        EmployeeLeave returnedEmployeeLeave = employeeLeaveRepository.findById(employeeLeaveDTO.getId())
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));

        long approverId = ExtractUserAuthentication.getCurrentUser().getId();
        String approverRole = ExtractUserAuthentication.getCurrentUser().getAuthorities().iterator().next().toString();

        // Setting Approver Id extracting from ExtractUserAuthentication currentUser id
        Employee approverEmployee = new Employee();
        approverEmployee.setId(approverId);

        // Employee cant approve their own request
        // If employee is user then must be his supervisor
        if(approverId == returnedEmployeeLeave.getEmployee().getId() ||
                (approverRole.equals("ROLE_USER") &&
                        (returnedEmployeeLeave.getEmployee().getEmployeeSupervisor()!= null &&
                                returnedEmployeeLeave.getEmployee().getEmployeeSupervisor().getId()!= null &&
                                approverId != returnedEmployeeLeave.getEmployee().getEmployeeSupervisor().getId()))){
            throw new UnauthorizedRequest(ExceptionConstants.YOU_CANT_REVIEW_THIS_REQUEST);
        }
        returnedEmployeeLeave.setIsApproved(employeeLeaveDTO.getIsApproved());
        returnedEmployeeLeave.setReviewedByEmployee(approverEmployee);
        returnedEmployeeLeave.setDeniedReason(employeeLeaveDTO.getDeniedReason());
        return EmployeeLeaveMapper.mapToDto(employeeLeaveRepository.save(returnedEmployeeLeave));
    }

    /**
     * Delete EmployeeLeave request that is still pending
     * EmployeeLeave Record must be present in database else throws Exception
     * EmployeeLeave status must be in pending else throws Exception
     *
     * @param id
     * @return boolean value of EmployeeLeave Deletion
     */
    @Override
    public EmployeeLeaveDTO ChangeEmployeeLeaveStatus(Long id) {

        EmployeeLeave returnedEmployeeLeave = employeeLeaveRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));

        // EmployeeLeave status must be in pending
        if(returnedEmployeeLeave.getReviewedByEmployee() != null){
            throw new DataConflictException(ExceptionConstants.EMPLOYEE_LEAVE_ACTION_ALREADY_TAKEN);
        }
        returnedEmployeeLeave.setStatus(0);
        return EmployeeLeaveMapper.mapToDto(employeeLeaveRepository.save(returnedEmployeeLeave));
    }
}
