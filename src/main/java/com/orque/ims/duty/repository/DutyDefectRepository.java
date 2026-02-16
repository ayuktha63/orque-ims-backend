package com.orque.ims.duty.repository;

import com.orque.ims.duty.entity.DutyDefect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DutyDefectRepository extends JpaRepository<DutyDefect,Long> {

    List<DutyDefect> findByJobId(String jobId);

    List<DutyDefect> findByCreatedBy(String createdBy);

    // ⭐ NEW
    List<DutyDefect> findByJobIdIn(List<String> jobIds);
}
