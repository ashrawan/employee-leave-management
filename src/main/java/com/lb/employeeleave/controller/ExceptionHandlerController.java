package com.lb.employeeleave.controller;

import com.lb.employeeleave.exceptions.DataConflictException;
import com.lb.employeeleave.exceptions.DataNotFoundException;
import com.lb.employeeleave.exceptions.ExceptionResponse;
import com.lb.employeeleave.exceptions.UnauthorizedRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> dataNotFoundException(final DataNotFoundException ex, final HttpServletRequest request) {

        LOGGER.info("DataNotFoundException handled");
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setCallerUrl(request.getRequestURI());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity<?> dataConflictException(final DataConflictException ex, final HttpServletRequest request) {

        LOGGER.info("DataConflictException handled");
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setCallerUrl(request.getRequestURI());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedRequest.class)
    public ResponseEntity<?> unauthorizedRequest(final UnauthorizedRequest ex, final HttpServletRequest request) {

        LOGGER.info("UnauthorizedRequest handled");
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setCallerUrl(request.getRequestURI());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(final IllegalArgumentException ex, final HttpServletRequest request) {

        LOGGER.info("IllegalArgumentException handled");
        ex.printStackTrace();
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setCallerUrl(request.getRequestURI());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> methodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException ex, final HttpServletRequest request) {

        LOGGER.info("MethodArgumentTypeMismatchException handled");
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setCallerUrl(request.getRequestURI());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }
}