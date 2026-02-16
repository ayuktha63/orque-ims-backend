package com.orque.ims.duty.service;

import com.orque.ims.duty.dto.CreateDefectRequest;
import com.orque.ims.duty.entity.DutyDefect;
import com.orque.ims.duty.repository.DutyDefectRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DutyDefectService {

    private final DutyDefectRepository repo;

    public DutyDefectService(DutyDefectRepository repo){
        this.repo = repo;
    }

    // ================= CREATE =================
    public DutyDefect create(CreateDefectRequest req, Authentication auth){

        DutyDefect d = new DutyDefect();
        d.setJobId(req.jobId());
        d.setIssue(req.issue());
        d.setStatus("OPEN");

        // ⭐ save username from JWT
        d.setCreatedBy(auth.getName());

        return repo.save(d);
    }

    // ================= ADMIN =================
    public List<DutyDefect> findAll(){
        return repo.findAll();
    }

    // ================= USER =================
    public List<DutyDefect> findByUser(String username){
        return repo.findByCreatedBy(username);
    }
}
