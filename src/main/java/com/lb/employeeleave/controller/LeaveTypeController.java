package com.lb.employeeleave.controller;

import com.lb.employeeleave.entity.LeaveType;
import com.lb.employeeleave.service.LeaveTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;

    public LeaveTypeController(final LeaveTypeService leaveTypeService){
        this.leaveTypeService = leaveTypeService;
    }

    @GetMapping("leave-types")
    public ResponseEntity<List<LeaveType>> retrieveAllLeaveTypes(){
        List<LeaveType> leaveTypeList = leaveTypeService.getAllLeaveTypes();
        if(leaveTypeList == null){
            return new ResponseEntity<>(leaveTypeList, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(leaveTypeList, HttpStatus.OK);
    }

    @GetMapping("leave-type/{id}")
    public ResponseEntity<LeaveType> retrieveLeaveType(@PathVariable long id){
        LeaveType leaveTypeRe = leaveTypeService.getLeaveTypeById(id);
        if(leaveTypeRe == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(leaveTypeRe, HttpStatus.OK);
    }

    @PostMapping("leave-type")
    public ResponseEntity<LeaveType> createLeaveType(@RequestBody LeaveType leaveType){
        LeaveType leaveTypeRe = leaveTypeService.createLeaveType(leaveType);
        if(leaveTypeRe == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(leaveTypeRe, HttpStatus.OK);
    }

    @PutMapping("leave-type")
    public ResponseEntity<LeaveType> updateLeaveType(@RequestBody LeaveType leaveType){
        LeaveType leaveTypeRe = leaveTypeService.updateLeaveType(leaveType);
        if(leaveTypeRe == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(leaveTypeRe, HttpStatus.OK);
    }
}
