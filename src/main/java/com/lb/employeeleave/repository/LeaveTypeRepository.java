package com.lb.employeeleave.repository;

import com.lb.employeeleave.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {

    LeaveType findByTypeName(String typeName);
    List<LeaveType> findAllByTypeNameContaining(String typeName);
}
