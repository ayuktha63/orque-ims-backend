package com.orque.ims.finance.service;

import com.orque.ims.finance.dto.*;
import com.orque.ims.finance.entity.FinanceEntry;
import com.orque.ims.finance.repository.FinanceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinanceService {
    private final FinanceRepository repository;

    public FinanceService(FinanceRepository repository) {
        this.repository = repository;
    }

    public List<FinanceResponse> list() {
        return repository.findAllByOrderByDateDescIdDesc().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // NEW: Get single entry by ID
    public FinanceResponse getById(Long id) {
        FinanceEntry entry = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Finance entry not found with id: " + id));
        return mapToResponse(entry);
    }

    @Transactional
    public FinanceResponse create(CreateFinanceRequest req) {
        FinanceEntry entry = new FinanceEntry();
        updateEntryFields(entry, req);
        return mapToResponse(repository.save(entry));
    }

    // NEW: Update existing entry
    @Transactional
    public FinanceResponse update(Long id, CreateFinanceRequest req) {
        FinanceEntry entry = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Finance entry not found with id: " + id));

        updateEntryFields(entry, req);
        return mapToResponse(repository.save(entry));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Finance entry not found");
        }
        repository.deleteById(id);
    }

    // Helper method to avoid code duplication between create and update
    private void updateEntryFields(FinanceEntry entry, CreateFinanceRequest req) {
        entry.setDate(req.date());
        entry.setType(req.type());
        entry.setCategory(req.category());
        entry.setAmount(req.amount());
        entry.setPaymentMode(req.paymentMode());
        entry.setDescription(req.description());
    }

    private FinanceResponse mapToResponse(FinanceEntry e) {
        return new FinanceResponse(e.getId(), e.getDate(), e.getType(),
                e.getCategory(), e.getAmount(), e.getPaymentMode(),
                e.getDescription(), e.getCreatedAt());
    }
}