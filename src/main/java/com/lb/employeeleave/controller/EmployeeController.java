package com.lb.employeeleave.controller;

import com.lb.employeeleave.entity.Employee;
import com.lb.employeeleave.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Retrieve all Employees.
     * Http Get method must be specified.
     * Url must be set on - server-url/base-path/employees
     * The data is returned in JSON format
     *
     * @param pageable
     * @return List of Employee in JSON format
     */
    @GetMapping
    public ResponseEntity<?> retrieveAllEmployees(@PageableDefault(page = 0, size = 10, sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable) {

        LOGGER.info("Returning all Employee´s");
        return new ResponseEntity<>( employeeService.getAllEmployees(pageable), HttpStatus.OK);
    }


    /**
     * Retrieve single Employee.
     * Http Get method must be specified.
     * Url must be set on - server-url/base-path/employee/{id}
     * The data is returned in JSON format
     *
     * @param id id of Employee that we want to retrieve
     * @return Employee in JSON format
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveEmployee(@PathVariable long id) {

        LOGGER.info("Returning single Employee");
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }


    /**
     * Create Employee.
     * Http Post method must be specified.
     * Url must be set on - server-url/base-path/employee
     * The data is returned in JSON format
     *
     * @param employee JSON data specifying employee to add
     * @return Created Employee in JSON format
     */
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {

        LOGGER.info("Returning created Employee");
        return new ResponseEntity<>(employeeService.createEmployee(employee), HttpStatus.OK);

    }

    /**
     * Update Employee
     * Http Put method must be specified.
     * Url must be set on - server-url/base-path/employee
     * The data is returned in JSON format
     *
     * @param employee JSON data specifying Employee to update
     * @return Created Employee in JSON format
     */
    @PutMapping
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {

        LOGGER.info("Returning updated Employee");
        return new ResponseEntity<>(employeeService.updateEmployee(employee), HttpStatus.OK);

    }

}
