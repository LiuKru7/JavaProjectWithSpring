package com.example.firstSpring.controller;

import com.example.firstSpring.dto.InvoiceDTO;
import com.example.firstSpring.enums.InvoiceStatus;
import com.example.firstSpring.repository.InvoiceRepository;
import com.example.firstSpring.repository.TaxRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final InvoiceRepository invoiceRepository;
    private final TaxRepository taxRepository;

    public InvoiceController(InvoiceRepository invoiceRepository, TaxRepository taxRepository) {
        this.invoiceRepository = invoiceRepository;
        this.taxRepository = taxRepository;
    }

    @PostMapping("/tax/{taxId}")
    public ResponseEntity<InvoiceDTO> addNewInvoice(@PathVariable int taxId, @RequestBody InvoiceDTO invoice) {
        return taxRepository.findById(taxId)
                .map(tax -> {
                    invoice.setTaxes(tax);
                    invoice.setStatus(InvoiceStatus.RECEIVED);
                    invoice.setReceiveDate(Date.valueOf(LocalDate.now()));
                    InvoiceDTO savedInvoice = invoiceRepository.save(invoice);
                    tax.addInvoice(savedInvoice);
                    taxRepository.save(tax);
                    return ResponseEntity.ok(savedInvoice);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDTO> getInvoice(@PathVariable int id) {
        return invoiceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices() {
        List<InvoiceDTO> invoices = invoiceRepository.findAll();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/tax/{taxId}")
    public ResponseEntity<List<InvoiceDTO>> getInvoicesByTaxId(@PathVariable int taxId) {
        if (!taxRepository.existsById(taxId)) {
            return ResponseEntity.notFound().build();
        }
        List<InvoiceDTO> invoices = invoiceRepository.findByTaxesId(taxId);
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<InvoiceDTO>> getInvoicesByStatus(@PathVariable InvoiceStatus status) {
        List<InvoiceDTO> invoices = invoiceRepository.findByStatus(status);
        return ResponseEntity.ok(invoices);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDTO> updateInvoice(@PathVariable int id, @RequestBody InvoiceDTO invoiceDetails) {
        return invoiceRepository.findById(id)
                .map(existingInvoice -> {
                    existingInvoice.setAmount(invoiceDetails.getAmount());
                    existingInvoice.setStatus(invoiceDetails.getStatus());
                    if (invoiceDetails.getStatus() == InvoiceStatus.PAID) {
                        existingInvoice.setPaidDate(Date.valueOf(LocalDate.now()));
                    }
                    InvoiceDTO updatedInvoice = invoiceRepository.save(existingInvoice);
                    return ResponseEntity.ok(updatedInvoice);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<InvoiceDTO> payInvoice(@PathVariable int id) {
        return invoiceRepository.findById(id)
                .map(invoice -> {
                    invoice.setStatus(InvoiceStatus.PAID);
                    invoice.setPaidDate(Date.valueOf(LocalDate.now()));
                    InvoiceDTO updatedInvoice = invoiceRepository.save(invoice);
                    return ResponseEntity.ok(updatedInvoice);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable int id) {
        if (!invoiceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        invoiceRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
