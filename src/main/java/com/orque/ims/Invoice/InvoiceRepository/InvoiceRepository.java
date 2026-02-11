package com.orque.ims.Invoice.InvoiceRepository;


import com.orque.ims.Invoice.InvoiceEntity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}