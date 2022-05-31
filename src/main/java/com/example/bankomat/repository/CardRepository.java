package com.example.bankomat.repository;

import com.example.bankomat.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {
    Optional<Card> findByCode(String code);
}
