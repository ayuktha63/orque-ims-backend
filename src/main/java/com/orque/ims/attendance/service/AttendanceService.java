package com.orque.ims.attendance.service;

import com.orque.ims.attendance.entity.Attendance;
import com.orque.ims.attendance.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public Attendance saveOrUpdateRecord(Attendance dto) {
        // We only want to save 'Leave' (or non-Present) in DB
        if ("Present".equalsIgnoreCase(dto.getStatus())) {
            // If it's already in DB as Leave, we should delete it because default is Present
            Optional<Attendance> existingOPT = attendanceRepository.findByEmployeeIdAndDate(dto.getEmployeeId(), dto.getDate());
            existingOPT.ifPresent(record -> attendanceRepository.delete(record));
            return dto; // Return the DTO as if it was saved
        }
        
        // Check if there is already a record for this employee on this date
        Optional<Attendance> existingOPT = attendanceRepository.findByEmployeeIdAndDate(dto.getEmployeeId(), dto.getDate());
        
        Attendance record;
        if (existingOPT.isPresent()) {
            record = existingOPT.get();
            record.setStatus(dto.getStatus()); 
            // Also update name if needed
            record.setEmployeeName(dto.getEmployeeName());
        } else {
            record = dto;
        }
        return attendanceRepository.save(record);
    }

    public void deleteRecord(String employeeId, String date) {
        LocalDate recordDate = LocalDate.parse(date);
        Optional<Attendance> existing = attendanceRepository.findByEmployeeIdAndDate(employeeId, recordDate);
        
        existing.ifPresent(record -> attendanceRepository.delete(record));
    }
}
