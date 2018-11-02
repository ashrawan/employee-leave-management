package com.lb.employeeleave.controller;

import com.lb.employeeleave.dto.LeaveTypeDTO;
import com.lb.employeeleave.service.LeaveTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/rest/leave-types")
public class LeaveTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaveTypeController.class);

    private final LeaveTypeService leaveTypeService;

    public LeaveTypeController(final LeaveTypeService leaveTypeService){
        this.leaveTypeService = leaveTypeService;
    }

    @GetMapping
    public ResponseEntity<?> retrieveAllLeaveTypes(){

        LOGGER.info("API Retrieve all LeaveTypes");
        return new ResponseEntity<>(leaveTypeService.getAllLeaveTypes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveLeaveType(@PathVariable long id){

        LOGGER.info("API Retrieve single LeaveType");
        return new ResponseEntity<>(leaveTypeService.getLeaveTypeById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createLeaveType(@RequestBody LeaveTypeDTO leaveTypeDTO){

        LOGGER.info("API Create LeaveType");
        return new ResponseEntity<>( leaveTypeService.createLeaveType(leaveTypeDTO), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateLeaveType(@RequestBody LeaveTypeDTO leaveTypeDTO){

        LOGGER.info("API Update LeaveType");
        return new ResponseEntity<>(leaveTypeService.updateLeaveType(leaveTypeDTO), HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<?> retrieveLeaveTypeByName(@PathParam("q") String q){

        LOGGER.info("API Retrieve single LeaveType");
        return new ResponseEntity<>(leaveTypeService.searchOnLeaveType(q), HttpStatus.OK);
    }
}
