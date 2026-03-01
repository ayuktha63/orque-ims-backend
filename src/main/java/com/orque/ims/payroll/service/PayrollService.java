package com.orque.ims.payroll.service;

import com.orque.ims.payroll.dto.*;
import com.orque.ims.payroll.entity.Payroll;
import com.orque.ims.payroll.repository.PayrollRepository;
import com.orque.ims.employee.repository.EmployeeRepository;

import com.orque.ims.finance.entity.FinanceEntry;
import com.orque.ims.finance.repository.FinanceRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PayrollService {

    private final PayrollRepository repository;
    private final EmployeeRepository employeeRepository;
    private final FinanceRepository financeRepository;

    public PayrollService(
            PayrollRepository repository,
            EmployeeRepository employeeRepository,
            FinanceRepository financeRepository
    ) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
        this.financeRepository = financeRepository;
    }

    public List<PayrollResponse> list(Optional<String> month) {
        List<Payroll> payrolls = month.isPresent()
                ? repository.findByMonthOrderByIdDesc(month.get())
                : repository.findAll();

        return payrolls.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public PayrollResponse getById(Long id) {
        Payroll p = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payroll not found"));
        return mapToResponse(p);
    }

    // =============================
    // CREATE
    // =============================

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

        Payroll saved = repository.save(payroll);

        // 🔥 CREATE FINANCE ENTRY
        FinanceEntry finance = new FinanceEntry();
        finance.setDate(LocalDate.now());
        finance.setType("EXPENSE");
        finance.setCategory("SALARY");
        finance.setAmount(saved.getNetPay());
        finance.setPaymentMode("BANK");
        finance.setDescription(
                "Salary paid to " + employee.getName() + " for " + saved.getMonth()
        );
        finance.setReferenceId(saved.getId());
        finance.setReferenceType("PAYROLL");

        financeRepository.save(finance);

        return mapToResponse(saved);
    }

    // =============================
    // UPDATE
    // =============================

    @Transactional
    public PayrollResponse update(Long id, CreatePayrollRequest req) {

        Payroll payroll = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payroll not found"));

        payroll.setMonth(req.month());
        payroll.setBasic(req.basic());
        payroll.setAllowances(req.allowances());
        payroll.setDeductions(req.deductions());
        payroll.setNotes(req.notes());

        calculateAndSetNetPay(payroll, req.basic(), req.allowances(), req.deductions());

        Payroll updated = repository.save(payroll);

        // 🔥 UPDATE RELATED FINANCE ENTRY
        financeRepository
                .findByReferenceIdAndReferenceType(id, "PAYROLL")
                .ifPresent(finance -> {
                    finance.setAmount(updated.getNetPay());
                    finance.setDescription(
                            "Salary paid to " +
                                    payroll.getEmployee().getName() +
                                    " for " + updated.getMonth()
                    );
                    financeRepository.save(finance);
                });

        return mapToResponse(updated);
    }

    // =============================
    // DELETE
    // =============================

    @Transactional
    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Payroll not found");
        }

        // 🔥 DELETE RELATED FINANCE ENTRY
        financeRepository
                .findByReferenceIdAndReferenceType(id, "PAYROLL")
                .ifPresent(financeRepository::delete);

        repository.deleteById(id);
    }

    // =============================
    // HELPERS
    // =============================

    private void calculateAndSetNetPay(
            Payroll p,
            BigDecimal basic,
            BigDecimal allow,
            BigDecimal deduct
    ) {
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
                p.getEmployee().getEmail(),   // ✅ ADD THIS LINE
                p.getBasic(),
                p.getAllowances(),
                p.getDeductions(),
                p.getNetPay(),
                p.getNotes(),
                p.getCreatedAt()
        );
    }
}