package com.orque.ims.payroll.controller;

import com.orque.ims.payroll.dto.CreatePayrollRequest;
import com.orque.ims.payroll.dto.PayrollResponse;
import com.orque.ims.payroll.service.PayrollService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/payroll")
public class PayrollController {

    private final PayrollService service;

    public PayrollController(PayrollService service) {
        this.service = service;
    }

    @GetMapping
    public List<PayrollResponse> getAll(@RequestParam(required = false) String month) {
        return service.list(Optional.ofNullable(month));
    }

    // NEW: Get By ID
    @GetMapping("/{id}")
    public PayrollResponse getOne(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PayrollResponse create(@Valid @RequestBody CreatePayrollRequest request) {
        return service.create(request);
    }

    // NEW: Update
    @PutMapping("/{id}")
    public PayrollResponse update(@PathVariable("id") Long id, @Valid @RequestBody CreatePayrollRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}