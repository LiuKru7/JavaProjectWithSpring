package com.example.firstSpring.dto;

import com.example.firstSpring.enums.InvoiceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Entity
@Table(name = "invoices")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;
    private double amount;
    @ManyToOne
    @JoinColumn(name = "tax_id", nullable = false)
    private TaxDTO taxes;
    @CreationTimestamp
    private Date receiveDate ;
    private Date paidDate;


    public InvoiceDTO(double amount) {
        this.status = InvoiceStatus.RECEIVED;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "InvoiceDTO{" +
                "amount=" + amount +
                ", status=" + status +
                ", id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TaxDTO getTaxes() {
        return taxes;
    }

    public void setTaxes(TaxDTO taxes) {
        this.taxes = taxes;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }
}
