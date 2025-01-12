package com.example.firstSpring.repository;

import com.example.firstSpring.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserDTO, Integer> {
}