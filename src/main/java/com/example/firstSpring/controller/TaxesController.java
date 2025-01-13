package com.example.firstSpring.controller;

import com.example.firstSpring.repository.TaxesRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/taxes")
public class TaxesController {
    private final TaxesRepository taxesRepository;
    public TaxesController(TaxesRepository taxesRepository) {
        this.taxesRepository = taxesRepository;
    }


}

