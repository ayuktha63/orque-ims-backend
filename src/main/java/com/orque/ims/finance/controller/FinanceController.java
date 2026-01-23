package com.orque.ims.finance.controller;

import com.orque.ims.finance.dto.*;
import com.orque.ims.finance.service.FinanceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/finance")
public class FinanceController {
    private final FinanceService service;

    public FinanceController(FinanceService service) {
        this.service = service;
    }

    @GetMapping
    public List<FinanceResponse> getAll() {
        return service.list();
    }

    // NEW: Get One
    @GetMapping("/{id}")
    public FinanceResponse getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FinanceResponse create(@Valid @RequestBody CreateFinanceRequest request) {
        return service.create(request);
    }

    // NEW: Update
    @PutMapping("/{id}")
    public FinanceResponse update(@PathVariable Long id, @Valid @RequestBody CreateFinanceRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}