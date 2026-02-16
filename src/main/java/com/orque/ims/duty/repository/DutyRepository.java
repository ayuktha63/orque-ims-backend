package com.orque.ims.duty.repository;

import com.orque.ims.duty.entity.Duty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DutyRepository extends JpaRepository<Duty, Long> {

    // ⭐ FIXED → matches Employee.employeeCode
    List<Duty> findByAssignedToEmployeeCode(String employeeCode);

    List<Duty> findByAssignedTo_Id(Long empId);
}
