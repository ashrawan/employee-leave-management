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

import java.util.Optional;

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
        Page<EmployeeLeave> employeeLeavePage = employeeLeaveRepository.findAll(pageable);
        Page<EmployeeLeaveDTO> employeeLeaveDTOPage = employeeLeavePage.map(employeeLeave -> EmployeeLeaveMapper.convertToDto(employeeLeave));
        return employeeLeaveDTOPage;
    }

    /**
     * Get single EmployeeLeave Record
     * @param id
     * @return If present EmployeeLeave else throw Exception
     */
    @Override
    public EmployeeLeaveDTO getEmployeeLeaveById(Long id) {
        Optional<EmployeeLeave> employeeLeave = employeeLeaveRepository.findById(id);
        if(!employeeLeave.isPresent()){
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND);
        }
        return EmployeeLeaveMapper.convertToDto(employeeLeave.get());
    }

    /**
     * Create New EmployeeLeave Request
     * @param employeeLeaveDTO
     * @return saved EmployeeLeave
     */
    @Override
    public EmployeeLeaveDTO createEmployeeLeave(EmployeeLeaveDTO employeeLeaveDTO) {
        EmployeeLeave employeeLeave = employeeLeaveRepository.save(EmployeeLeaveMapper.convertToEntity(employeeLeaveDTO));
        return EmployeeLeaveMapper.convertToDto(employeeLeave);
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

        Optional<EmployeeLeave> returnedEmployeeLeave = employeeLeaveRepository.findById(employeeLeaveDTO.getId());
        // EmployeeLeave Record must be present in database
        if(!returnedEmployeeLeave.isPresent()){
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND);
        }
        // EmployeeLeave status must be in pending
        if(returnedEmployeeLeave.get().getReviewedByEmployee() != null){
            throw new DataConflictException(ExceptionConstants.EMPLOYEE_LEAVE_ACTION_ALREADY_TAKEN);
        }
        EmployeeLeave employeeLeave1 = returnedEmployeeLeave.get();
        employeeLeave1.setLeaveDateFrom(employeeLeaveDTO.getLeaveDateFrom());
        employeeLeave1.setLeaveDateTo(employeeLeaveDTO.getLeaveDateTo());
        employeeLeave1.setLeaveReason(employeeLeaveDTO.getLeaveReason());
        return EmployeeLeaveMapper.convertToDto(employeeLeaveRepository.save(employeeLeave1));
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

        Optional<EmployeeLeave> returnedEmployeeLeave = employeeLeaveRepository.findById(employeeLeaveDTO.getId());
        // EmployeeLeave Record must be present in database
        if(!returnedEmployeeLeave.isPresent()){
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND);
        }

        long approverId = ExtractUserAuthentication.getCurrentUser().getId();
        String approverRole = ExtractUserAuthentication.getCurrentUser().getAuthorities().iterator().next().toString();

        // Setting Approver Id extracting from ExtractUserAuthentication currentUser id
        Employee approverEmployee = new Employee();
        approverEmployee.setId(approverId);

        EmployeeLeave employeeLeave1 = returnedEmployeeLeave.get();

        // Employee cant approve their own request
        // If employee is user then must be his supervisor
        if(approverId == employeeLeave1.getEmployee().getId() ||
                (approverRole.equals("ROLE_USER") &&
                        (employeeLeave1.getEmployee().getEmployeeSupervisor()!= null &&
                                employeeLeave1.getEmployee().getEmployeeSupervisor().getId()!= null &&
                                approverId != employeeLeave1.getEmployee().getEmployeeSupervisor().getId()))){
            throw new UnauthorizedRequest(ExceptionConstants.YOU_CANT_REVIEW_THIS_REQUEST);
        }

        employeeLeave1.setIsApproved(employeeLeaveDTO.getIsApproved());
        employeeLeave1.setReviewedByEmployee(approverEmployee);
        employeeLeave1.setDeniedReason(employeeLeaveDTO.getDeniedReason());
        return EmployeeLeaveMapper.convertToDto(employeeLeaveRepository.save(employeeLeave1));
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

        Optional<EmployeeLeave> returnedEmployeeLeave = employeeLeaveRepository.findById(id);
        // EmployeeLeave Record must be present in database
        if(!returnedEmployeeLeave.isPresent()){
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND);
        }
        // EmployeeLeave status must be in pending
        if(returnedEmployeeLeave.get().getReviewedByEmployee() != null){
            throw new DataConflictException(ExceptionConstants.EMPLOYEE_LEAVE_ACTION_ALREADY_TAKEN);
        }
        EmployeeLeave employeeLeave = returnedEmployeeLeave.get();
        employeeLeave.setStatus(0);
        return EmployeeLeaveMapper.convertToDto(employeeLeaveRepository.save(employeeLeave));
    }
}
