package com.lb.employeeleave.service;

import com.lb.employeeleave.dto.LeaveReportDTO;

import java.util.List;

public interface ReportService {

    List<LeaveReportDTO> retrieveLeaveReports();
}
