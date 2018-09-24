package com.lb.employeeleave.serviceImpl;

import com.lb.employeeleave.constant.ExceptionConstants;
import com.lb.employeeleave.entity.EmployeeLeave;
import com.lb.employeeleave.exceptions.DataNotFoundException;
import com.lb.employeeleave.repository.EmployeeLeaveRepository;
import com.lb.employeeleave.service.EmployeeLeaveService;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<EmployeeLeave> getAllEmployeeLeaves() {
        return employeeLeaveRepository.findAll();
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
     * Can only be updated by Employee if EmployeeLeave request status is pending-0
     * @param employeeLeave
     * @return updated EmployeeLeave
     */
    @Override
    public EmployeeLeave updateEmployeeLeave(EmployeeLeave employeeLeave) {
        Optional<EmployeeLeave> returnedEmployeeLeave = employeeLeaveRepository.findById(employeeLeave.getId());
        if(!returnedEmployeeLeave.isPresent()){
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND);
        }
        // Is EmployeeLeave status still pending
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
     * Can only be update by EmployeeSupervisor or Admin
     * @param employeeLeave
     * @return approved EmployeeLeave
     */
    @Override
    public EmployeeLeave approveEmployeeLeave(EmployeeLeave employeeLeave) {
        Optional<EmployeeLeave> returnedEmployeeLeave = employeeLeaveRepository.findById(employeeLeave.getId());
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
     * Can only be deleted by Employee
     * Must be Deleted prior to the leave request starting date
     * @param id
     * @return boolean value of EmployeeLeave Deletion
     */
    @Override
    public boolean deletePendingEmployeeLeave(Long id) {
        Optional<EmployeeLeave> returnedEmployeeLeave = employeeLeaveRepository.findById(id);
        if(!returnedEmployeeLeave.isPresent()){
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND);
        }
        // leave request date must be less than current date
        employeeLeaveRepository.delete(returnedEmployeeLeave.get());
        return true;
    }
}
