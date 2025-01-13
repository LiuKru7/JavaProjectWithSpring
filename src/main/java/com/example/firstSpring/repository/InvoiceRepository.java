package com.example.firstSpring.repository;

import com.example.firstSpring.dto.InvoiceDTO;
import com.example.firstSpring.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceDTO, Integer> {
    List<InvoiceDTO> findByTaxesId(int taxId);
    List<InvoiceDTO> findByStatus(InvoiceStatus status);
}
