package com.lb.employeeleave.controller;

import com.lb.employeeleave.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/reports")
@RestController
public class ReportController {

    private final ReportService reportService;

    public ReportController(final ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/leaveReport")
    public ResponseEntity<?> retrieveLeaveReport(){
        return new ResponseEntity<>(reportService.retrieveLeaveReports(), HttpStatus.OK);
    }
}
