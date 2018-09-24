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
    @Override
    public List<EmployeeLeave> getAllEmployeeLeaves() {
        return employeeLeaveRepository.findAll();
    }

    @Override
    public EmployeeLeave getEmployeeLeaveById(Long id) {
        return employeeLeaveRepository.findById(id).orElseThrow(()-> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));
    }

    @Override
    public EmployeeLeave createEmployeeLeave(EmployeeLeave employeeLeave) {
        return employeeLeaveRepository.save(employeeLeave);
    }

    @Override
    public EmployeeLeave updateEmployeeLeave(EmployeeLeave employeeLeave) {
        Optional<EmployeeLeave> returnedEmployeeLeave = employeeLeaveRepository.findById(employeeLeave.getId());
        if(!returnedEmployeeLeave.isPresent()){
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND);
        }
        EmployeeLeave employeeLeave1 = returnedEmployeeLeave.get();
        employeeLeave1.setLeaveType(employeeLeave.getLeaveType());
        employeeLeave1.setLeaveDateFrom(employeeLeave.getLeaveDateFrom());
        employeeLeave1.setLeaveDateTo(employeeLeave.getLeaveDateTo());
        employeeLeave1.setLeaveReason(employeeLeave.getLeaveReason());
        return employeeLeaveRepository.save(employeeLeave1);
    }

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

    @Override
    public boolean deletePendingEmployeeLeave(Long id) {
        Optional<EmployeeLeave> returnedEmployeeLeave = employeeLeaveRepository.findById(id);
        if(!returnedEmployeeLeave.isPresent()){
            throw new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND);
        }
        employeeLeaveRepository.delete(returnedEmployeeLeave.get());
        return true;
    }
}
