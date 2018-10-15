package com.lb.employeeleave.serviceImpl;

import com.lb.employeeleave.constant.ExceptionConstants;
import com.lb.employeeleave.dto.LeaveTypeDTO;
import com.lb.employeeleave.entity.LeaveType;
import com.lb.employeeleave.exceptions.DataNotFoundException;
import com.lb.employeeleave.mapper.LeaveTypeMapper;
import com.lb.employeeleave.repository.LeaveTypeRepository;
import com.lb.employeeleave.service.LeaveTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        return leaveTypeList
                .stream()
                .map((leaveType) -> LeaveTypeMapper.convertToDto(leaveType))
                .collect(Collectors.toList());
    }

    /**
     * Get single LeaveType Record
     * @param id
     * @return If present LeaveType else throws Exception
     */
    @Override
    public LeaveTypeDTO getLeaveTypeById(Long id) {
        Optional<LeaveType> leaveType = leaveTypeRepository.findById(id);
        if(!leaveType.isPresent()){
            throw new DataNotFoundException(ExceptionConstants.LEAVE_TYPE_RECORD_NOT_FOUND);
        }
        return LeaveTypeMapper.convertToDto(leaveType.get());
    }

    /**
     * Create New LeaveType
     * @param leaveTypeDTO
     * @return saved LeaveType
     */
    @Override
    public LeaveTypeDTO createLeaveType(LeaveTypeDTO leaveTypeDTO) {
        LeaveType leaveType = leaveTypeRepository.save(LeaveTypeMapper.convertToEntity(leaveTypeDTO));
        return LeaveTypeMapper.convertToDto(leaveType);
    }

    /**
     * Update LeaveType
     * LeaveType must be present in database else throws Exception
     * @param leaveTypeDTO
     * @return updated LeaveType
     */
    @Override
    public LeaveTypeDTO updateLeaveType(LeaveTypeDTO leaveTypeDTO) {
        Optional<LeaveType> returnedLeaveType = leaveTypeRepository.findById(leaveTypeDTO.getId());
        if(!returnedLeaveType.isPresent()){
            throw new DataNotFoundException(ExceptionConstants.LEAVE_TYPE_RECORD_NOT_FOUND);
        }
        LeaveType leaveType = returnedLeaveType.get();
        leaveType.setTypeName(leaveTypeDTO.getTypeName());
        leaveType.setStatus(leaveTypeDTO.getStatus());
        return LeaveTypeMapper.convertToDto(leaveTypeRepository.save(leaveType));
    }
}
