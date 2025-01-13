package com.example.firstSpring.repository;

import com.example.firstSpring.dto.TaxDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaxRepository extends JpaRepository<TaxDTO, Integer> {
    List<TaxDTO> findByUserId(int userId);
}