package com.lb.employeeleave.mapper;

import com.lb.employeeleave.util.enums.EmployeeStatus;
import com.lb.employeeleave.util.enums.LeaveStatus;
import com.lb.employeeleave.util.enums.LeaveTypeStatus;

public class StatusMapper {

    public static EmployeeStatus mapEmployeeStatus(String status) {

        if(status == null){
            return EmployeeStatus.INACTIVE;
        }

        if (status.equals(String.valueOf(EmployeeStatus.ACTIVE))) {
            return EmployeeStatus.ACTIVE;
        } else {
            return EmployeeStatus.INACTIVE;
        }
    }

    public static LeaveStatus mapLeaveStatus(String status) {

        if(status == null){
            return LeaveStatus.PENDING;
        }

        if (status.equals(String.valueOf(LeaveStatus.APPROVED))) {
            return LeaveStatus.APPROVED;
        } else if (status.equals(String.valueOf(LeaveStatus.DENIED))) {
            return LeaveStatus.DENIED;
        } else {
            return LeaveStatus.PENDING;
        }
    }

    public static LeaveTypeStatus mapLeaveTypeStatus(String status) {

        if(status == null){
            return LeaveTypeStatus.INACTIVE;
        }

        if (status.equals(String.valueOf(LeaveTypeStatus.ACTIVE))) {
            return LeaveTypeStatus.ACTIVE;
        } else {
            return LeaveTypeStatus.INACTIVE;
        }
    }

}
