package com.lb.employeeleave.repository;

import com.lb.employeeleave.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Override
    Page<Employee> findAll(Pageable pageable);

    // All employee under supervision of given employee
    List<Employee> findAllByEmployeeSupervisor(Employee employee);

    Page<Employee> findByFullNameStartingWithIgnoreCase(Pageable pageable, String fullName);
}
