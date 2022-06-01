package com.example.bankomat.controller;

import com.example.bankomat.repository.MoneyRepository;
import com.example.bankomat.service.MoneyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author * Sunnatullayev Mahmudnazar *  * bankomat *  * 19:03 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/money")
public class MoneyController {
    final MoneyRepository moneyRepository;
    final MoneyService moneyService;


//    public ResponseEntity<?> addMoney()
}
