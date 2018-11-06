package com.lb.employeeleave.repository;

import com.lb.employeeleave.util.enums.EmployeeStatus;
import com.lb.employeeleave.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Override
    Page<Employee> findAll(Pageable pageable);

    Employee findByUsername(String username);

    Employee findByUsernameAndStatus(String username, EmployeeStatus status);

    // All employee under supervision of given employee
    List<Employee> findAllBySupervisor(Employee employee);

    Page<Employee> findByFirstNameContainingOrMiddleNameContainingOrLastNameContaining(Pageable pageable, String firstName, String middleName, String lastName);
}
