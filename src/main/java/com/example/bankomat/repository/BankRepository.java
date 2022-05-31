package com.example.bankomat.repository;

import com.example.bankomat.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank,Integer> {
}
