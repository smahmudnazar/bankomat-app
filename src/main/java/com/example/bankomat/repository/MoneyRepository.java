package com.example.bankomat.repository;
import com.example.bankomat.entity.Money;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyRepository extends JpaRepository<Money,Integer> {
}
