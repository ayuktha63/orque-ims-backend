package com.orque.ims.duty.controller;

import com.orque.ims.duty.dto.CreateDefectRequest;
import com.orque.ims.duty.entity.DutyDefect;
import com.orque.ims.duty.service.DutyDefectService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/defects")
@CrossOrigin
public class DutyDefectController {

    private final DutyDefectService service;

    public DutyDefectController(DutyDefectService service){
        this.service = service;
    }

    // CREATE
    @PostMapping
    public DutyDefect create(@RequestBody CreateDefectRequest req, Authentication auth){
        return service.create(req, auth);
    }

    // ADMIN → ALL
    @GetMapping
    public List<DutyDefect> all(){
        return service.findAll();
    }

    // USER → OWN DEFECTS
    @GetMapping("/my")
    public List<DutyDefect> my(Authentication auth){
        return service.findByUser(auth.getName());
    }
}
