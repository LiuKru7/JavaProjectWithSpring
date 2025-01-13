package com.example.firstSpring.repository;

import com.example.firstSpring.dto.TaxDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxesRepository extends JpaRepository<TaxDTO, Integer> {
}
