package com.lb.employeeleave.controller;

import com.lb.employeeleave.entity.EmployeeLeave;
import com.lb.employeeleave.service.EmployeeLeaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeLeaveController {

    private final EmployeeLeaveService employeeLeaveService;

    public EmployeeLeaveController(final EmployeeLeaveService employeeLeaveService) {
        this.employeeLeaveService = employeeLeaveService;
    }

    @GetMapping("employee-leaves")
    public ResponseEntity<List<EmployeeLeave>> retrieveAllEmployeeLeaves(){

        List<EmployeeLeave> employeeLeaveList = employeeLeaveService.getAllEmployeeLeaves();
        if(employeeLeaveList == null){
            return new ResponseEntity<>(employeeLeaveList, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<List<EmployeeLeave>>(employeeLeaveList, HttpStatus.OK);
    }

    @GetMapping("employee-leave/{id}")
    public ResponseEntity<EmployeeLeave> retrieveEmployeeLeave(@PathVariable long id){
        EmployeeLeave employeeLeaveRe = employeeLeaveService.getEmployeeLeaveById(id);
        if(employeeLeaveRe == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employeeLeaveRe, HttpStatus.OK);
    }

    @PostMapping("employee-leave")
    public ResponseEntity<EmployeeLeave> createEmployeeLeave(@RequestBody EmployeeLeave employeeLeave){
        EmployeeLeave employeeLeaveRe = employeeLeaveService.createEmployeeLeave(employeeLeave);
        if(employeeLeave == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<EmployeeLeave>(employeeLeave, HttpStatus.OK);
    }

    @PutMapping("employee-leave/{id}")
    public ResponseEntity<EmployeeLeave> updateEmployeeLeave(@RequestBody EmployeeLeave employeeLeave, @PathVariable long id){
        EmployeeLeave employeeLeaveRe = employeeLeaveService.updateEmployeeLeave(employeeLeave);
        if(employeeLeaveRe == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<EmployeeLeave>(employeeLeaveRe, HttpStatus.OK);
    }

    @PutMapping("approve-employee-leave/{id}")
    public ResponseEntity<EmployeeLeave> approveEmployeeLeave(@RequestBody EmployeeLeave employeeLeave, @PathVariable long id){
        EmployeeLeave employeeLeaveRe = employeeLeaveService.approveEmployeeLeave(employeeLeave);
        if(employeeLeaveRe == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<EmployeeLeave>(employeeLeave, HttpStatus.OK);
    }

    @PutMapping("delete-pending-request/{id}")
    public ResponseEntity<Boolean> deletePendingLeaveRequest(@PathVariable long id){
        boolean b = employeeLeaveService.deletePendingEmployeeLeave(id);
        if(!b){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Boolean>(b, HttpStatus.OK);
    }
}
