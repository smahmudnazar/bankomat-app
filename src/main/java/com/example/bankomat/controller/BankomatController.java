package com.example.bankomat.controller;

import com.example.bankomat.dto.ApiResponse;
import com.example.bankomat.dto.BankomatDTO;
import com.example.bankomat.entity.Bankomat;
import com.example.bankomat.repository.BankomatRepository;
import com.example.bankomat.service.BankomatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bankomat")
public class BankomatController {
    final BankomatRepository bankomatRepository;
    final BankomatService bankomatService;

    @PreAuthorize("hasAnyAuthority('BANKOMAT_CRUD', 'READ_BANKOMAT')")
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(bankomatRepository.findAll());
    }

    @PreAuthorize("hasAnyAuthority('BANKOMAT_CRUD', 'READ_BANKOMAT')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        Optional<Bankomat> bankOptional = bankomatRepository.findById(id);
        if (bankOptional.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(bankOptional.get());
    }

    @PreAuthorize("hasAnyAuthority('BANKOMAT_CRUD', 'DELETE_BANKOMAT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<Bankomat> bankOptional = bankomatRepository.findById(id);
        if (bankOptional.isEmpty()) return ResponseEntity.notFound().build();
        bankomatRepository.delete(bankOptional.get());

        return ResponseEntity.ok().body("Deleted");
    }

    @PreAuthorize("hasAnyAuthority('BANKOMAT_CRUD', 'ADD_BANKOMAT')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody BankomatDTO bank) {
        ApiResponse apiResponse = bankomatService.save(bank);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @PreAuthorize("hasAnyAuthority('BANKOMAT_CRUD', 'ADD_BANKOMAT')")
    @PutMapping
    public ResponseEntity<?> edit(@RequestBody BankomatDTO bank, @PathVariable Integer id) {
        ApiResponse apiResponse = bankomatService.edit(bank, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }


}
