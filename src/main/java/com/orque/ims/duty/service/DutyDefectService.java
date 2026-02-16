package com.orque.ims.duty.service;

import com.orque.ims.duty.dto.CreateDefectRequest;
import com.orque.ims.duty.entity.Duty;
import com.orque.ims.duty.entity.DutyDefect;
import com.orque.ims.duty.repository.DutyDefectRepository;
import com.orque.ims.duty.repository.DutyRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DutyDefectService {

    private final DutyDefectRepository repo;
    private final DutyRepository dutyRepo;

    public DutyDefectService(
            DutyDefectRepository repo,
            DutyRepository dutyRepo
    ){
        this.repo = repo;
        this.dutyRepo = dutyRepo;
    }

    // ================= CREATE =================
    public DutyDefect create(CreateDefectRequest req, Authentication auth){

        DutyDefect d = new DutyDefect();
        d.setJobId(req.jobId());
        d.setIssue(req.issue());
        d.setStatus("OPEN");

        // ⭐ JWT name = employeeCode
        d.setCreatedBy(auth.getName());

        return repo.save(d);
    }

    // ================= ADMIN =================
    public List<DutyDefect> findAll(){
        return repo.findAll();
    }

    // ================= MY RAISED =================
    public List<DutyDefect> findByUser(String employeeCode){
        return repo.findByCreatedBy(employeeCode);
    }

    // ================= ASSIGNED TO ME =================
    public List<DutyDefect> findAssignedToMe(String employeeCode){

        // ⭐ FIXED METHOD NAME
        List<Duty> duties =
                dutyRepo.findByAssignedToEmployeeCode(employeeCode);

        List<String> jobIds = duties.stream()
                .map(Duty::getJobId)
                .toList();

        if(jobIds.isEmpty()) return List.of();

        return repo.findByJobIdIn(jobIds);
    }
}
