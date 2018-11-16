package com.lb.employeeleave.service;

import com.lb.employeeleave.dto.LeaveReportDTO;
import com.lb.employeeleave.repository.LeaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final LeaveRepository leaveRepository;

    public ReportServiceImpl(final LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    @Override
    public List<LeaveReportDTO> retrieveLeaveReports() {
        return leaveRepository.generateLeaveReport();
    }
}
