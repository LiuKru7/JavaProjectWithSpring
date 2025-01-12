package com.example.firstSpring.dto;

import com.example.firstSpring.enums.TaxesType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "taxes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDTO user;

    @Enumerated(EnumType.STRING)
    private TaxesType type;
    private double balance;

    @OneToMany (mappedBy =  "taxes", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvoiceDTO> invoices;


    public TaxDTO(TaxesType type, double balance) {
        this.type = type;
        this.balance = balance;

    }


    @Override
    public String toString() {
        return "TaxDTO{" +
                "id=" + id +
                ", type=" + type +
                ", balance=" + balance +
                '}';
    }

    public void addInvoice(InvoiceDTO invoice) {
        if (invoices == null) {
            invoices = new ArrayList<>();
        }
        invoices.add(invoice);
        invoice.setTaxes(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public TaxesType getType() {
        return type;
    }

    public void setType(TaxesType type) {
        this.type = type;
    }

    public List<InvoiceDTO> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceDTO> invoices) {
        this.invoices = invoices;
    }
}
