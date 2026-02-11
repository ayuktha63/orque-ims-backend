package com.orque.ims.Invoice.InvoiceController;

import com.orque.ims.Invoice.InvoiceEntity.Invoice;
import com.orque.ims.Invoice.InvoiceService.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "http://localhost:4200") // Matches your Angular port
public class InvoiceController {

    @Autowired
    private InvoiceService service;

    @GetMapping
    public List<Invoice> getAll() {
        return service.getAllInvoices();
    }

    @PostMapping
    public Invoice create(@RequestBody Invoice invoice) {
        return service.saveInvoice(invoice); //
    }
}