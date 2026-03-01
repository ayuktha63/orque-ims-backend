package com.orque.ims.employee.service;

import com.orque.ims.employee.dto.*;
import com.orque.ims.employee.entity.Employee;
import com.orque.ims.employee.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<EmployeeResponse> list() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmployeeResponse create(CreateEmployeeRequest req) {

        if (repository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Employee emp = new Employee();
        emp.setName(req.name());
        emp.setEmail(req.email());   // ✅ SET EMAIL
        emp.setDepartment(req.department());
        emp.setRole(req.role());
        emp.setJoinDate(req.joinDate());
        emp.setStatus(req.status());

        String lastCode = repository.findMaxEmployeeCode();
        int nextId = (lastCode == null)
                ? 1
                : Integer.parseInt(lastCode.replace("EMP", "")) + 1;

        emp.setEmployeeCode(String.format("EMP%04d", nextId));

        return mapToResponse(repository.save(emp));
    }

    public EmployeeResponse getById(Long id) {
        Employee emp = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Employee not found with id: " + id));

        return mapToResponse(emp);
    }

    @Transactional
    public EmployeeResponse update(Long id, CreateEmployeeRequest req) {

        Employee emp = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Employee not found with id: " + id));

        emp.setName(req.name());
        emp.setEmail(req.email());   // ✅ UPDATE EMAIL
        emp.setDepartment(req.department());
        emp.setRole(req.role());
        emp.setJoinDate(req.joinDate());
        emp.setStatus(req.status());

        return mapToResponse(repository.save(emp));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Employee not found");
        }
        repository.deleteById(id);
    }

    private EmployeeResponse mapToResponse(Employee e) {
        return new EmployeeResponse(
                e.getId(),
                e.getEmployeeCode(),
                e.getName(),
                e.getEmail(),     // ✅ INCLUDE EMAIL
                e.getDepartment(),
                e.getRole(),
                e.getJoinDate(),
                e.getStatus(),
                e.getCreatedAt()
        );
    }
}