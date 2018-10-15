package com.lb.employeeleave.mapper;

import com.lb.employeeleave.dto.EmployeeLeaveDTO;
import com.lb.employeeleave.entity.EmployeeLeave;

public class EmployeeLeaveMapper {

    public static EmployeeLeaveDTO convertToDto(EmployeeLeave employeeLeave){
        EmployeeLeaveDTO employeeLeaveDTO = new EmployeeLeaveDTO();
        employeeLeaveDTO.setId(employeeLeave.getId());
        if(employeeLeave.getEmployee() != null) {
            employeeLeaveDTO.setEmployee(EmployeeMapper.convertToDto(employeeLeave.getEmployee()));
        }
        if(employeeLeave.getLeaveType() != null) {
            employeeLeaveDTO.setLeaveType(LeaveTypeMapper.convertToDto(employeeLeave.getLeaveType()));
        }
        employeeLeaveDTO.setLeaveReason(employeeLeave.getLeaveReason());
        employeeLeaveDTO.setLeaveDateFrom(employeeLeave.getLeaveDateFrom());
        employeeLeaveDTO.setLeaveDateTo(employeeLeave.getLeaveDateTo());
        employeeLeaveDTO.setIsApproved(employeeLeave.getIsApproved());
        employeeLeaveDTO.setDeniedReason(employeeLeave.getDeniedReason());
        employeeLeaveDTO.setStatus(employeeLeave.getStatus());
        employeeLeaveDTO.setCreatedDateTime(employeeLeave.getCreatedDateTime());
        if(employeeLeave.getReviewedByEmployee() != null) {
            employeeLeaveDTO.setReviewedByEmployee(EmployeeMapper.convertToDto(employeeLeave.getReviewedByEmployee()));
        }
        return employeeLeaveDTO;
    }

    public static EmployeeLeave convertToEntity(EmployeeLeaveDTO employeeLeaveDTO){
        EmployeeLeave employeeLeave = new EmployeeLeave();
        employeeLeave.setId(employeeLeaveDTO.getId());
        if(employeeLeaveDTO.getEmployee() != null) {
            employeeLeave.setEmployee(EmployeeMapper.convertToEntity(employeeLeaveDTO.getEmployee()));
        }
        if(employeeLeaveDTO.getLeaveType() != null) {
            employeeLeave.setLeaveType(LeaveTypeMapper.convertToEntity(employeeLeaveDTO.getLeaveType()));
        }
        employeeLeave.setLeaveReason(employeeLeaveDTO.getLeaveReason());
        employeeLeave.setLeaveDateFrom(employeeLeaveDTO.getLeaveDateFrom());
        employeeLeave.setLeaveDateTo(employeeLeaveDTO.getLeaveDateTo());
        employeeLeave.setIsApproved(employeeLeaveDTO.getIsApproved());
        employeeLeave.setDeniedReason(employeeLeaveDTO.getDeniedReason());
        employeeLeave.setStatus(employeeLeaveDTO.getStatus());
        employeeLeave.setCreatedDateTime(employeeLeaveDTO.getCreatedDateTime());
        if(employeeLeaveDTO.getReviewedByEmployee() != null) {
            employeeLeave.setReviewedByEmployee(EmployeeMapper.convertToEntity(employeeLeaveDTO.getReviewedByEmployee()));
        }
        return employeeLeave;
    }
}
