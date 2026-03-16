package com.orque.ims.attendance.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "attendance_records")
public class Attendance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "employee_id", nullable = false)
    private String employeeId;
    
    @Column(name = "employee_name", nullable = false)
    private String employeeName;
    
    @Column(name = "record_date", nullable = false)
    private LocalDate date;
    
    @Column(nullable = false)
    private String status; // 'Present' or 'Leave'
}
