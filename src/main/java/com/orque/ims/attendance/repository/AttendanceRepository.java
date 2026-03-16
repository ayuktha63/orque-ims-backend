package com.orque.ims.attendance.repository;

import com.orque.ims.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    
    // Find a specific record for an employee on a specific date (useful for updates)
    Optional<Attendance> findByEmployeeIdAndDate(String employeeId, LocalDate date);
    
    // Optional: Find all leaves in a given date range (for the upcoming leaves dashboard)
    List<Attendance> findByStatusAndDateBetween(String status, LocalDate startDate, LocalDate endDate);
}
