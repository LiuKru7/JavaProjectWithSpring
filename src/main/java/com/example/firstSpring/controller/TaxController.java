package com.example.firstSpring.controller;

import com.example.firstSpring.dto.TaxDTO;
import com.example.firstSpring.repository.TaxRepository;
import com.example.firstSpring.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/taxes")
public class TaxController {
    private final TaxRepository taxRepository;
    private final UserRepository userRepository;

    public TaxController(TaxRepository taxRepository, UserRepository userRepository) {
        this.taxRepository = taxRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<TaxDTO> addNewTax(@PathVariable int userId, @RequestBody TaxDTO tax) {
        return userRepository.findById(userId)
                .map(user -> {
                    tax.setUser(user);
                    TaxDTO savedTax = taxRepository.save(tax);
                    user.addTax(savedTax);
                    userRepository.save(user);
                    return ResponseEntity.ok(savedTax);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxDTO> getTax(@PathVariable int id) {
        return taxRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TaxDTO>> getAllTaxes() {
        List<TaxDTO> taxes = taxRepository.findAll();
        return ResponseEntity.ok(taxes);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaxDTO>> getTaxesByUserId(@PathVariable int userId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        List<TaxDTO> taxes = taxRepository.findByUserId(userId);
        return ResponseEntity.ok(taxes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaxDTO> updateTax(@PathVariable int id, @RequestBody TaxDTO taxDetails) {
        return taxRepository.findById(id)
                .map(existingTax -> {
                    existingTax.setType(taxDetails.getType());
                    existingTax.setBalance(taxDetails.getBalance());
                    // Don't update user or invoices through this endpoint
                    TaxDTO updatedTax = taxRepository.save(existingTax);
                    return ResponseEntity.ok(updatedTax);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTax(@PathVariable int id) {
        if (!taxRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taxRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
