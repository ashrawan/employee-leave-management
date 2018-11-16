package com.lb.employeeleave.mapper;

import com.lb.employeeleave.dto.LeaveDTO;
import com.lb.employeeleave.entity.Leave;

public class LeaveMapper {

    public static LeaveDTO mapToDto(Leave employeeLeave){
        LeaveDTO employeeLeaveDTO = new LeaveDTO();
        employeeLeaveDTO.setLeaveId(employeeLeave.getLeaveId());
        if(employeeLeave.getEmployee() != null) {
            employeeLeaveDTO.setEmployeeDTO(EmployeeMapper.mapToDto(employeeLeave.getEmployee()));
        }
        if(employeeLeave.getLeaveType() != null) {
            employeeLeaveDTO.setLeaveTypeDTO(LeaveTypeMapper.mapToDto(employeeLeave.getLeaveType()));
        }
        employeeLeaveDTO.setLeaveReason(employeeLeave.getLeaveReason());
        employeeLeaveDTO.setFromDate(employeeLeave.getFromDate());
        employeeLeaveDTO.setToDate(employeeLeave.getToDate());
        employeeLeaveDTO.setDeniedReason(employeeLeave.getDeniedReason());
        employeeLeaveDTO.setStatus(String.valueOf(employeeLeave.getStatus()));
        employeeLeaveDTO.setCreatedAt(employeeLeave.getCreatedAt());
        if(employeeLeave.getReviewedBy() != null) {
            employeeLeaveDTO.setReviewedBy(EmployeeMapper.mapToDto(employeeLeave.getReviewedBy()));
        }
        return employeeLeaveDTO;
    }

    public static Leave mapToEntity(LeaveDTO employeeLeaveDTO){
        Leave employeeLeave = new Leave();
        employeeLeave.setLeaveId(employeeLeaveDTO.getLeaveId());
        if(employeeLeaveDTO.getEmployeeDTO() != null) {
            employeeLeave.setEmployee(EmployeeMapper.mapToEntity(employeeLeaveDTO.getEmployeeDTO()));
        }
        if(employeeLeaveDTO.getLeaveTypeDTO() != null) {
            employeeLeave.setLeaveType(LeaveTypeMapper.mapToEntity(employeeLeaveDTO.getLeaveTypeDTO()));
        }
        employeeLeave.setLeaveReason(employeeLeaveDTO.getLeaveReason());
        employeeLeave.setFromDate(employeeLeaveDTO.getFromDate());
        employeeLeave.setToDate(employeeLeaveDTO.getToDate());
        employeeLeave.setDeniedReason(employeeLeaveDTO.getDeniedReason());
        employeeLeave.setStatus(StatusMapper.mapLeaveStatus(employeeLeaveDTO.getStatus()));
        employeeLeave.setCreatedAt(employeeLeaveDTO.getCreatedAt());
        if(employeeLeaveDTO.getReviewedBy() != null) {
            employeeLeave.setReviewedBy(EmployeeMapper.mapToEntity(employeeLeaveDTO.getReviewedBy()));
        }
        return employeeLeave;
    }
}
