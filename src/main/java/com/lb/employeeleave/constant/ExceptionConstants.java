package com.lb.employeeleave.constant;

public class ExceptionConstants {

    // Employee Exception Constant
    public static final String EMPLOYEE_RECORD_NOT_FOUND = "Employee doesn't exists";
    public static final String EMPLOYEE_USERNAME_NOT_VALID = "Employee username not valid";
    public static final String EMPLOYEE_SUPERVISOR_MISMATCH = "Employee supervisor mismatch";

    // Leave Exception Constant
    public static final String EMPLOYEE_LEAVE_RECORD_NOT_FOUND = "Employee leave record not found";
    public static final String EMPLOYEE_LEAVE_ACTION_ALREADY_TAKEN = "Action already taken on this leave request";

    // LeaveType Exception Constant
    public static final String LEAVE_TYPE_RECORD_NOT_FOUND = "Leave type not found";
    public static final String LEAVE_TYPE_NAME_NOT_VALID = "Leave type name not valid";

    // Unauthorized Request
    public static final String YOU_CANT_REVIEW_THIS_REQUEST = "You cant review this request";

}
