package com.lb.employeeleave.mapper;

import com.lb.employeeleave.dto.LeaveTypeDTO;
import com.lb.employeeleave.entity.LeaveType;

public class LeaveTypeMapper {

    public static LeaveTypeDTO convertToDto(LeaveType leaveType) {
        LeaveTypeDTO leaveTypeDTO = new LeaveTypeDTO();
        leaveTypeDTO.setId(leaveType.getId());
        leaveTypeDTO.setTypeName(leaveType.getTypeName());
        leaveTypeDTO.setStatus(leaveType.getStatus());
        return leaveTypeDTO;
    }

    public static LeaveType convertToEntity(LeaveTypeDTO leaveTypeDTO) {
        LeaveType leaveType = new LeaveType();
        leaveType.setId(leaveTypeDTO.getId());
        leaveType.setTypeName(leaveTypeDTO.getTypeName());
        leaveType.setStatus(leaveTypeDTO.getStatus());
        return leaveType;
    }
}
