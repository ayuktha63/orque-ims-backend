package com.orque.ims.duty.repository;

import com.orque.ims.duty.entity.Duty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DutyRepository extends JpaRepository<Duty,Long> {

    List<Duty> findByAssignedTo_Id(Long empId);

    List<Duty> findByStatus(String status);

    Optional<Duty> findByJobId(String jobId);
}
