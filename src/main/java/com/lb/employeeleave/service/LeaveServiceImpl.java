package com.lb.employeeleave.service;

import com.lb.employeeleave.constant.ExceptionConstants;
import com.lb.employeeleave.constant.enums.LeaveStatus;
import com.lb.employeeleave.dto.EmployeeDTO;
import com.lb.employeeleave.dto.LeaveDTO;
import com.lb.employeeleave.entity.Employee;
import com.lb.employeeleave.entity.Leave;
import com.lb.employeeleave.exceptions.DataConflictException;
import com.lb.employeeleave.exceptions.DataNotFoundException;
import com.lb.employeeleave.exceptions.UnauthorizedRequest;
import com.lb.employeeleave.mapper.LeaveMapper;
import com.lb.employeeleave.repository.LeaveRepository;
import com.lb.employeeleave.security.ExtractUserAuthentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository employeeLeaveRepository;

    public LeaveServiceImpl(final LeaveRepository employeeLeaveRepository){
        this.employeeLeaveRepository = employeeLeaveRepository;
    }

    /**
     * Get all Leave Record
     * @return List of Leave
     */
    @Override
    public Page<LeaveDTO> getAllEmployeeLeaves(Pageable pageable) {

        return employeeLeaveRepository.findAll(pageable)
                .map(employeeLeave -> LeaveMapper.mapToDto(employeeLeave));
    }

    /**
     * Get single Leave Record
     * @param id
     * @return If present Leave else throw Exception
     */
    @Override
    public LeaveDTO getEmployeeLeaveById(Long id) {

        Leave employeeLeave = employeeLeaveRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));
        return LeaveMapper.mapToDto(employeeLeave);
    }

    /**
     * Create New Leave Request
     * @param leaveDTO
     * @return saved Leave
     */
    @Override
    public LeaveDTO createEmployeeLeave(LeaveDTO leaveDTO) {

        if(leaveDTO.getLeaveTypeDTO() == null){
            throw new DataNotFoundException(ExceptionConstants.LEAVE_TYPE_RECORD_NOT_FOUND);
        }
        // Setting current employee id on current newly created Leave Request
        long employeeId = ExtractUserAuthentication.getCurrentUser().getId();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employeeId);

        leaveDTO.setEmployeeDTO(employeeDTO);
        leaveDTO.setStatus(LeaveStatus.PENDING);
        Leave employeeLeave = employeeLeaveRepository.save(LeaveMapper.mapToEntity(leaveDTO));
        return LeaveMapper.mapToDto(employeeLeave);
    }

    /**
     * Update Leave
     * Leave Record must be present in database else throws Exception
     * Leave status must be in pending else throws Exception
     * @param leaveDTO
     * @return updated Leave
     */
    @Override
    public LeaveDTO updateEmployeeLeave(LeaveDTO leaveDTO) {

        Leave returnedEmployeeLeave = employeeLeaveRepository.findById(leaveDTO.getLeaveId())
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));

        // Leave status must be in pending
        if(returnedEmployeeLeave.getReviewedBy() != null){
            throw new DataConflictException(ExceptionConstants.EMPLOYEE_LEAVE_ACTION_ALREADY_TAKEN);
        }
        returnedEmployeeLeave.setFromDate(leaveDTO.getFromDate());
        returnedEmployeeLeave.setToDate(leaveDTO.getToDate());
        returnedEmployeeLeave.setLeaveReason(leaveDTO.getLeaveReason());
        return LeaveMapper.mapToDto(employeeLeaveRepository.save(returnedEmployeeLeave));
    }

    /**
     * Approved Leave request
     * Leave Record must be present in database else throws Exception
     *
     * @param leaveDTO
     * @return approved Leave
     */
    @Override
    public LeaveDTO approveEmployeeLeave(LeaveDTO leaveDTO) {

        Leave returnedEmployeeLeave = employeeLeaveRepository.findById(leaveDTO.getLeaveId())
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));

        long approverId = ExtractUserAuthentication.getCurrentUser().getId();
        String approverRole = ExtractUserAuthentication.getCurrentUser().getAuthorities().iterator().next().toString();

        // Setting Approver Id extracting from ExtractUserAuthentication currentUser id
        Employee approverEmployee = new Employee();
        approverEmployee.setEmployeeId(approverId);

        // Employee cant approve their own request
        // If employee is user then must be his supervisor
        if(approverId == returnedEmployeeLeave.getEmployee().getEmployeeId() ||
                (approverRole.equals("ROLE_USER") &&
                        (returnedEmployeeLeave.getEmployee().getSupervisor()!= null &&
                                returnedEmployeeLeave.getEmployee().getSupervisor().getEmployeeId()!= null &&
                                approverId != returnedEmployeeLeave.getEmployee().getSupervisor().getEmployeeId()))){
            throw new UnauthorizedRequest(ExceptionConstants.YOU_CANT_REVIEW_THIS_REQUEST);
        }
        returnedEmployeeLeave.setApproved(leaveDTO.isApproved());
        returnedEmployeeLeave.setStatus(LeaveStatus.ACTIVE);
        returnedEmployeeLeave.setReviewedBy(approverEmployee);
        returnedEmployeeLeave.setDeniedReason(leaveDTO.getDeniedReason());
        return LeaveMapper.mapToDto(employeeLeaveRepository.save(returnedEmployeeLeave));
    }

    /**
     * Delete Leave request that is still pending
     * Leave Record must be present in database else throws Exception
     * Leave status must be in pending else throws Exception
     *
     * @param id
     * @return boolean value of Leave Deletion
     */
    @Override
    public LeaveDTO ChangeEmployeeLeaveStatus(Long id) {

        Leave returnedEmployeeLeave = employeeLeaveRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));

        // Leave status must be in pending
        if(returnedEmployeeLeave.getReviewedBy() != null){
            throw new DataConflictException(ExceptionConstants.EMPLOYEE_LEAVE_ACTION_ALREADY_TAKEN);
        }
        returnedEmployeeLeave.setStatus(LeaveStatus.INACTIVE);
        return LeaveMapper.mapToDto(employeeLeaveRepository.save(returnedEmployeeLeave));
    }
}
