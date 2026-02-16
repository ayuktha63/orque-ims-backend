package com.orque.ims.duty.service;

import com.orque.ims.duty.dto.CreateDutyRequest;
import com.orque.ims.duty.entity.Duty;
import com.orque.ims.duty.repository.DutyRepository;
import com.orque.ims.employee.entity.Employee;
import com.orque.ims.employee.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DutyService {

    private final DutyRepository repo;
    private final EmployeeRepository empRepo;

    public DutyService(DutyRepository repo, EmployeeRepository empRepo){
        this.repo = repo;
        this.empRepo = empRepo;
    }

    public List<Duty> list(){
        return repo.findAll();
    }

    @Transactional
    public Duty create(CreateDutyRequest req){
        Employee emp = empRepo.findById(req.employeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        Duty d = new Duty();
        d.setTitle(req.title());
        d.setDescription(req.description());
        d.setDeadline(req.deadline());
        d.setAssignedTo(emp);
        d.setStatus("PENDING_APPROVAL"); // Default initial status

        long count = repo.count() + 1;
        d.setJobId(String.format("JOB%04d", count));

        return repo.save(d);
    }

    @Transactional
    public Duty updateStatus(Long id, String status){
        Duty d = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Duty not found"));
        d.setStatus(status);
        return repo.save(d);
    }

    public List<Duty> myWork(Long empId){
        return repo.findByAssignedTo_Id(empId);
    }
}