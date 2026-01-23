package com.orque.ims.payroll.repository;

import com.orque.ims.payroll.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    /**
     * Finds the highest payroll code currently in the database.
     * Used for generating the next code (e.g., if max is PAY0005, next is PAY0006).
     */
    @Query("SELECT MAX(p.payrollCode) FROM Payroll p")
    String findMaxPayrollCode();

    /**
     * Retrieves all payroll records for a specific month (Format: YYYY-MM).
     * Ordered by ID descending so the newest records appear first.
     */
    List<Payroll> findByMonthOrderByIdDesc(String month);
}