package com.lb.employeeleave.constant;

public class ExceptionConstants {

    // Employee Exception Constant
    public static String EMPLOYEE_RECORD_NOT_FOUND = "employee doesn't exists";
    public static String EMPLOYEE_USERNAME_NOT_VALID = "employee username not valid";
    public static String EMPLOYEE_SUPERVISOR_MISMATCH = "employee supervisor mismatch";

    // Leave Exception Constant
    public static String EMPLOYEE_LEAVE_RECORD_NOT_FOUND = "employee leave record not found";
    public static String EMPLOYEE_LEAVE_ACTION_ALREADY_TAKEN = "action already taken on this leave request";

    // LeaveType Exception Constant
    public static String LEAVE_TYPE_RECORD_NOT_FOUND = "leave type not found";

    // Unauthorized Request
    public static String YOU_CANT_REVIEW_THIS_REQUEST = "You cant review this request";

}
