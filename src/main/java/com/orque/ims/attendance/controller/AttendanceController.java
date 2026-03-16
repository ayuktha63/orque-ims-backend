package com.orque.ims.attendance.controller;

import com.orque.ims.attendance.entity.Attendance;
import com.orque.ims.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*") // Adjust based on your security config
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // 1. GET ALL RECORDS (Used by the Calendar and Dashboard)
    @GetMapping
    public List<Attendance> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    // 2. CREATE OR UPDATE A RECORD (Called when clicking Calendar Dialog)
    @PostMapping
    public ResponseEntity<Attendance> saveOrUpdateRecord(@RequestBody Attendance dto) {
        Attendance savedRecord = attendanceService.saveOrUpdateRecord(dto);
        return ResponseEntity.ok(savedRecord);
    }
    
    // 3. DELETE A RECORD (Called if the user clears the status in the Dialog)
    @DeleteMapping("/{employeeId}/{date}")
    public ResponseEntity<Void> deleteRecord(@PathVariable String employeeId, @PathVariable String date) {
        attendanceService.deleteRecord(employeeId, date);
        return ResponseEntity.ok().build();
    }
}
