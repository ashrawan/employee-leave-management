package com.lb.employeeleave.service;

import com.lb.employeeleave.constant.ExceptionConstants;
import com.lb.employeeleave.constant.enums.Status;
import com.lb.employeeleave.dto.LeaveTypeDTO;
import com.lb.employeeleave.entity.LeaveType;
import com.lb.employeeleave.exceptions.DataNotFoundException;
import com.lb.employeeleave.mapper.LeaveTypeMapper;
import com.lb.employeeleave.repository.LeaveTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveTypeServiceImpl(final LeaveTypeRepository leaveTypeRepository){
        this.leaveTypeRepository = leaveTypeRepository;
    }

    /**
     * Get all LeaveTypes Record
     * @return List of LeaveType
     */
    @Override
    public List<LeaveTypeDTO> getAllLeaveTypes() {

        return leaveTypeRepository.findAll()
                .stream()
                .map((leaveType) -> LeaveTypeMapper.mapToDto(leaveType))
                .collect(Collectors.toList());
    }

    /**
     * Get single LeaveType Record
     * @param id
     * @return If present LeaveType else throws Exception
     */
    @Override
    public LeaveTypeDTO getLeaveTypeById(Long id) {

        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException(ExceptionConstants.LEAVE_TYPE_RECORD_NOT_FOUND));
        return LeaveTypeMapper.mapToDto(leaveType);
    }

    /**
     * Create New LeaveType
     * @param leaveTypeDTO
     * @return saved LeaveType
     */
    @Override
    public LeaveTypeDTO createLeaveType(LeaveTypeDTO leaveTypeDTO) {

        leaveTypeDTO.setStatus(Status.ACTIVE);
        LeaveType leaveType = leaveTypeRepository.save(LeaveTypeMapper.mapToEntity(leaveTypeDTO));
        return LeaveTypeMapper.mapToDto(leaveType);
    }

    /**
     * Update LeaveType
     * LeaveType must be present in database else throws Exception
     * @param leaveTypeDTO
     * @return updated LeaveType
     */
    @Override
    public LeaveTypeDTO updateLeaveType(LeaveTypeDTO leaveTypeDTO) {

        LeaveType returnedLeaveType = leaveTypeRepository.findById(leaveTypeDTO.getLeaveTypeId())
                .orElseThrow(()-> new DataNotFoundException(ExceptionConstants.LEAVE_TYPE_RECORD_NOT_FOUND));

        returnedLeaveType.setTypeName(leaveTypeDTO.getTypeName());
        returnedLeaveType.setStatus(leaveTypeDTO.getStatus());
        return LeaveTypeMapper.mapToDto(leaveTypeRepository.save(returnedLeaveType));
    }
}
