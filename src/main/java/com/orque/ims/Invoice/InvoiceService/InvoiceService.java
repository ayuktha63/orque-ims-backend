package com.orque.ims.Invoice.InvoiceService;

import com.orque.ims.Invoice.InvoiceEntity.Invoice;
import com.orque.ims.Invoice.InvoiceEntity.InvoiceItem;
import com.orque.ims.Invoice.InvoiceRepository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository repository;

    /**
     * Retrieves all invoices from the MySQL database.
     */
    public List<Invoice> getAllInvoices() {
        return repository.findAll();
    }

    /**
     * Calculates totals and saves the invoice to the database.
     */
    public Invoice saveInvoice(Invoice invoice) {
        // Ensure items list is not null to avoid errors during stream
        if (invoice.getItems() != null) {
            double totalAmount = invoice.getItems().stream()
                    .mapToDouble(InvoiceItem::getCost)
                    .sum();

            invoice.setTotal(totalAmount);
            invoice.setRemaining(totalAmount - invoice.getPaid());
        }

        // Set date to current if not provided by frontend
        if (invoice.getDate() == null) {
            invoice.setDate(LocalDate.now());
        }

        return repository.save(invoice);
    }
}