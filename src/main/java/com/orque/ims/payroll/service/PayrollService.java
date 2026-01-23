package com.orque.ims.payroll.service;

import com.orque.ims.payroll.dto.*;
import com.orque.ims.payroll.entity.Payroll;
import com.orque.ims.payroll.repository.PayrollRepository;
import com.orque.ims.employee.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PayrollService {
    private final PayrollRepository repository;
    private final EmployeeRepository employeeRepository;

    public PayrollService(PayrollRepository repository, EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
    }

    public List<PayrollResponse> list(Optional<String> month) {
        List<Payroll> payrolls;
        if (month.isPresent() && !month.get().isEmpty()) {
            payrolls = repository.findByMonthOrderByIdDesc(month.get());
        } else {
            payrolls = repository.findAll();
        }
        return payrolls.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // NEW: Get single payroll by ID
    public PayrollResponse getById(Long id) {
        Payroll p = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payroll record not found with id: " + id));
        return mapToResponse(p);
    }

    @Transactional
    public PayrollResponse create(CreatePayrollRequest req) {
        var employee = employeeRepository.findById(req.employeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        Payroll payroll = new Payroll();
        payroll.setEmployee(employee);
        payroll.setMonth(req.month());
        payroll.setBasic(req.basic());
        payroll.setAllowances(req.allowances());
        payroll.setDeductions(req.deductions());
        payroll.setNotes(req.notes());

        calculateAndSetNetPay(payroll, req.basic(), req.allowances(), req.deductions());

        String lastCode = repository.findMaxPayrollCode();
        int nextId = (lastCode == null) ? 1 : Integer.parseInt(lastCode.replace("PAY", "")) + 1;
        payroll.setPayrollCode(String.format("PAY%04d", nextId));

        return mapToResponse(repository.save(payroll));
    }

    // NEW: Update existing payroll
    @Transactional
    public PayrollResponse update(Long id, CreatePayrollRequest req) {
        Payroll payroll = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payroll record not found with id: " + id));

        // Note: employeeId usually doesn't change for a specific payroll record,
        // but we update fields and recalculate Net Pay.
        payroll.setMonth(req.month());
        payroll.setBasic(req.basic());
        payroll.setAllowances(req.allowances());
        payroll.setDeductions(req.deductions());
        payroll.setNotes(req.notes());

        calculateAndSetNetPay(payroll, req.basic(), req.allowances(), req.deductions());

        return mapToResponse(repository.save(payroll));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Payroll record not found with id: " + id);
        }
        repository.deleteById(id);
    }

    // Helper to keep math consistent
    private void calculateAndSetNetPay(Payroll p, BigDecimal basic, BigDecimal allow, BigDecimal deduct) {
        BigDecimal net = basic.add(allow).subtract(deduct);
        p.setNetPay(net.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : net);
    }

    private PayrollResponse mapToResponse(Payroll p) {
        return new PayrollResponse(
                p.getId(),
                p.getPayrollCode(),
                p.getMonth(),
                p.getEmployee().getId(),
                p.getEmployee().getEmployeeCode(),
                p.getEmployee().getName(),
                p.getBasic(),
                p.getAllowances(),
                p.getDeductions(),
                p.getNetPay(),
                p.getNotes(),
                p.getCreatedAt()
        );
    }
}