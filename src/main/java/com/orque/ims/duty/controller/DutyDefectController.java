package com.orque.ims.duty.controller;

import com.orque.ims.duty.dto.CreateDefectRequest;
import com.orque.ims.duty.entity.DutyDefect;
import com.orque.ims.duty.service.DutyDefectService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/defects")
@CrossOrigin
public class DutyDefectController {

    private final DutyDefectService service;

    public DutyDefectController(DutyDefectService service){
        this.service = service;
    }

    @PostMapping
    public DutyDefect create(@RequestBody CreateDefectRequest req){
        return service.create(req);
    }
}
