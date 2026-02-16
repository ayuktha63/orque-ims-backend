package com.orque.ims.duty.service;

import com.orque.ims.duty.dto.CreateDefectRequest;
import com.orque.ims.duty.entity.DutyDefect;
import com.orque.ims.duty.repository.DutyDefectRepository;
import org.springframework.stereotype.Service;

@Service
public class DutyDefectService {

    private final DutyDefectRepository repo;

    public DutyDefectService(DutyDefectRepository repo){
        this.repo = repo;
    }

    public DutyDefect create(CreateDefectRequest req){

        DutyDefect d = new DutyDefect();
        d.setJobId(req.jobId());
        d.setIssue(req.issue());
        d.setStatus("OPEN");

        return repo.save(d);
    }
}
