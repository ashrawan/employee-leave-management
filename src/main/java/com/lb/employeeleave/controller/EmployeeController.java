package com.lb.employeeleave.controller;

import com.lb.employeeleave.entity.Employee;
import com.lb.employeeleave.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Returns list of all employees.
     * Http Get Method must be specified.
     * Url must be set on - server-url/base-path/employees
     * The data is returned in JSON format
     *
     * @return List of employees in JSON format
     */
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> retrieveAllEmployees() {

        LOGGER.info("Returning all the Employee´s");
        List<Employee> employeeList = employeeService.getAllEmployees();
        if(employeeList==null || employeeList.size()==0 ){
            return new ResponseEntity(employeeList, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Employee>>(employeeList, HttpStatus.OK);
    }


    /**
     * Returns single employee.
     * Http Get Method must be specified.
     * Url must be set on - server-url/base-path/employee/{id}
     * The data is returned in JSON format
     *
     * @param id id of employee that we want to retrieve
     * @return List of employees in JSON format
     */
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> retrieveEmployee(@PathVariable long id) {

        Employee employeeRe = employeeService.getEmployeeById(id);
        if(employeeRe == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(employeeRe, HttpStatus.OK);
    }


    /**
     * Returns Created employee.
     * Http Post Method must be specified.
     * Url must be set on - server-url/base-path/employees
     * The data is returned in JSON format
     *
     * @param employee json data specifying user to add
     * @return Created employees in JSON format
     */
    @PostMapping("/employee")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {

        Employee employeeRe = employeeService.createEmployee(employee);
        if(employeeRe == null ){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Employee>(employeeRe, HttpStatus.OK);

    }

    /**
     * Returns Updated employee.
     * Http Put Method must be specified.
     * Url must be set on - server-url/base-path/employee/{id}
     * The data is returned in JSON format
     *
     * @param employee json data specifying employee to update
     * @return Created employee in JSON format
     */
    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable long id) {

        Employee employeeRe = employeeService.updateEmployee(employee);
        if(employeeRe == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Employee>(employeeRe, HttpStatus.OK);

    }

}
