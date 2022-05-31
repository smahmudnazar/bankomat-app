package com.example.bankomat.controller;

import com.example.bankomat.dto.ApiResponse;
import com.example.bankomat.dto.BankDTO;
import com.example.bankomat.entity.Bank;
import com.example.bankomat.repository.BankRepository;
import com.example.bankomat.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bank")
public class BankController {
    final BankRepository bankRepository;
    final BankService bankService;

    @PreAuthorize("hasAnyAuthority('BANK_CRUD', 'READ_BANK')")
    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body(bankRepository.findAll());
    }

    @PreAuthorize("hasAnyAuthority('BANK_CRUD', 'READ_BANK')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        Optional<Bank> bankOptional = bankRepository.findById(id);
        if(bankOptional.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(bankOptional.get());
    }

    @PreAuthorize("hasAnyAuthority('BANK_CRUD', 'ADD_BANK')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody BankDTO bank){
        ApiResponse apiResponse = bankService.save(bank);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }

    @PreAuthorize("hasAnyAuthority('BANK_CRUD', 'EDIT_BANK')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id,@RequestBody BankDTO bank){
        ApiResponse apiResponse = bankService.edit(id, bank);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }

    @PreAuthorize("hasAnyAuthority('BANK_CRUD', 'DELETE_BANK')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        Optional<Bank> bankOptional = bankRepository.findById(id);
        if (bankOptional.isEmpty()) return ResponseEntity.notFound().build();
        bankRepository.delete(bankOptional.get());

        return ResponseEntity.ok().body("Deleted");
    }
}
