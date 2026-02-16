package com.orque.ims.duty.controller;

import com.orque.ims.duty.dto.CreateDutyRequest;
import com.orque.ims.duty.entity.Duty;
import com.orque.ims.duty.service.DutyService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/duties")
@CrossOrigin
public class DutyController {
    private final DutyService service;
    public DutyController(DutyService service) { this.service = service; }

    @GetMapping
    public List<Duty> list() { return service.list(); }

    @PostMapping
    public Duty create(@RequestBody CreateDutyRequest req) { return service.create(req); }

    @PutMapping("/{id}/status")
    public Duty updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return service.updateStatus(id, body.get("status"));
    }

    @GetMapping("/my/{empId}")
    public List<Duty> myWork(@PathVariable Long empId) { return service.myWork(empId); }
}