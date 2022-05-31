package com.example.bankomat.repository;

import com.example.bankomat.entity.Banknot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BanknotRepository extends JpaRepository<Banknot,Integer> {
}
