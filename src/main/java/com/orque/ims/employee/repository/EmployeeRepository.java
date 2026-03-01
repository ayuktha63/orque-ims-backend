package com.orque.ims.employee.repository;

import com.orque.ims.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT MAX(e.employeeCode) FROM Employee e")
    String findMaxEmployeeCode();
    boolean existsByEmail(String email);
}