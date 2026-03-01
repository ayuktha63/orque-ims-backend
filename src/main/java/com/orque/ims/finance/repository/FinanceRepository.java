package com.orque.ims.finance.repository;

import com.orque.ims.finance.entity.FinanceEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinanceRepository extends JpaRepository<FinanceEntry, Long> {

    // Returns entries sorted by date (newest first)
    List<FinanceEntry> findAllByOrderByDateDescIdDesc();

    // 🔥 Find finance entry linked to Payroll
    Optional<FinanceEntry> findByReferenceIdAndReferenceType(
            Long referenceId,
            String referenceType
    );
}