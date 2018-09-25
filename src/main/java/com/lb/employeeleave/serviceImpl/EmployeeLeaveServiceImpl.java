package com.lb.employeeleave.serviceImpl;

import com.lb.employeeleave.constant.ExceptionConstants;
import com.lb.employeeleave.entity.EmployeeLeave;
import com.lb.employeeleave.exceptions.DataConflictException;
import com.lb.employeeleave.exceptions.DataNotFoundException;
import com.lb.employeeleave.repository.EmployeeLeaveRepository;
import com.lb.employeeleave.service.EmployeeLeaveService;
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
    public Page<EmployeeLeave> getAllEmployeeLeaves(Pageable pageable) {

        return employeeLeaveRepository.findAll(pageable);
    }

    /**
     * Get single EmployeeLeave Record
     * @param id
     * @return If present EmployeeLeave else throw Exception
     */
    @Override
    public EmployeeLeave getEmployeeLeaveById(Long id) {

        return employeeLeaveRepository.findById(id).orElseThrow(()-> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));
    }

    /**
     * Create New EmployeeLeave Request
     * @param employeeLeave
     * @return saved EmployeeLeave
     */
    @Override
    public EmployeeLeave createEmployeeLeave(EmployeeLeave employeeLeave) {

        return employeeLeaveRepository.save(employeeLeave);
    }

    /**
     * Update EmployeeLeave
     * EmployeeLeave Record must be present in database else throws Exception
     * EmployeeLeave status must be in pending else throws Exception
     * @param employeeLeave
     * @return updated EmployeeLeave
     */
    @Override
    public EmployeeLeave updateEmployeeLeave(EmployeeLeave employeeLeave) {

        Optional<EmployeeLeave> returnedEmployeeLeave = employeeLeaveRepository.findById(employeeLeave.getId());
        // EmployeeLeave Record must be present in database
        if(!returnedEmployeeLeave.isPresent()){
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND);
        }
        // EmployeeLeave status must be in pending
        if(returnedEmployeeLeave.get().getReviewedByEmployee() != null){
            throw new DataConflictException(ExceptionConstants.EMPLOYEE_LEAVE_ACTION_ALREADY_TAKEN);
        }
        EmployeeLeave employeeLeave1 = returnedEmployeeLeave.get();
        employeeLeave1.setLeaveType(employeeLeave.getLeaveType());
        employeeLeave1.setLeaveDateFrom(employeeLeave.getLeaveDateFrom());
        employeeLeave1.setLeaveDateTo(employeeLeave.getLeaveDateTo());
        employeeLeave1.setLeaveReason(employeeLeave.getLeaveReason());
        return employeeLeaveRepository.save(employeeLeave1);
    }

    /**
     * Approved EmployeeLeave request
     * EmployeeLeave Record must be present in database else throws Exception
     *
     * @param employeeLeave
     * @return approved EmployeeLeave
     */
    @Override
    public EmployeeLeave approveEmployeeLeave(EmployeeLeave employeeLeave) {

        Optional<EmployeeLeave> returnedEmployeeLeave = employeeLeaveRepository.findById(employeeLeave.getId());
        // EmployeeLeave Record must be present in database
        if(!returnedEmployeeLeave.isPresent()){
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND);
        }
        EmployeeLeave employeeLeave1 = returnedEmployeeLeave.get();
        employeeLeave1.setIsApproved(employeeLeave.getIsApproved());
        employeeLeave1.setReviewedByEmployee(employeeLeave.getReviewedByEmployee());
        employeeLeave1.setDeniedReason(employeeLeave.getDeniedReason());
        employeeLeave.setStatus(employeeLeave.getStatus());
        return employeeLeaveRepository.save(employeeLeave1);
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
    public boolean deletePendingEmployeeLeave(Long id) {

        Optional<EmployeeLeave> returnedEmployeeLeave = employeeLeaveRepository.findById(id);
        // EmployeeLeave Record must be present in database
        if(!returnedEmployeeLeave.isPresent()){
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND);
        }
        // EmployeeLeave status must be in pending
        if(returnedEmployeeLeave.get().getReviewedByEmployee() != null){
            throw new DataConflictException(ExceptionConstants.EMPLOYEE_LEAVE_ACTION_ALREADY_TAKEN);
        }
        employeeLeaveRepository.delete(returnedEmployeeLeave.get());
        return true;
    }
}
