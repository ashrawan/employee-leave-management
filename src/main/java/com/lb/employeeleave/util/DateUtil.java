package com.lb.employeeleave.util;

import com.lb.employeeleave.exceptions.DataConflictException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static LocalDate convertToDate(String date){
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        } catch (Exception e){
            throw new DataConflictException( ExceptionConstants.DATE_CANT_BE_PARSED +" : # "+date);
        }
        return localDate;
    }

    public static LocalDateTime convertToDateTime(String date){
        LocalDateTime localDateTime = null; 
        try {
            localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e){
            throw new DataConflictException( ExceptionConstants.DATE_CANT_BE_PARSED +" : "+date);
        }
        return localDateTime;
    }
}
