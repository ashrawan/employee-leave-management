package com.lb.employeeleave.util;

public class ExceptionConstants {

    // Employee Exception Constant
    public static final String EMPLOYEE_RECORD_NOT_FOUND = "Employee doesn't exists";
    public static final String EMPLOYEE_USERNAME_NOT_VALID = "Employee username not valid";
    public static final String OLD_PASSWORD_DOESNT_MATCH = "Old Password Doesn't Match";
    public static final String EMPLOYEE_SUPERVISOR_MISMATCH = "Employee supervisor mismatch";

    // Leave Exception Constant
    public static final String EMPLOYEE_LEAVE_RECORD_NOT_FOUND = "Employee leave record not found";
    public static final String EMPLOYEE_LEAVE_ACTION_ALREADY_TAKEN = "Action already taken on this leave request";

    // LeaveType Exception Constant
    public static final String LEAVE_TYPE_RECORD_NOT_FOUND = "Leave type not found";
    public static final String LEAVE_TYPE_NAME_NOT_VALID = "Leave type name not valid";

    // Event Exception Constant
    public static final String EVENT_RECORD_NOT_FOUND = "Event doesn't exists";

    // Unauthorized Request
    public static final String YOU_CANT_REVIEW_THIS_REQUEST = "You cant review this request";

    // Utils error
    public static final String DATE_CANT_BE_PARSED = "Date can't be parsed";

}
