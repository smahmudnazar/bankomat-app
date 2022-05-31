package com.example.bankomat.service;

import com.example.bankomat.dto.ApiResponse;
import com.example.bankomat.dto.BankDTO;
import com.example.bankomat.entity.Bank;
import com.example.bankomat.entity.Role;
import com.example.bankomat.entity.User;
import com.example.bankomat.repository.BankRepository;
import com.example.bankomat.repository.RoleRepository;
import com.example.bankomat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankService {
    final BankRepository bankRepository;
    final PasswordEncoder passwordEncoder;
    final RoleRepository roleRepository;
    final UserRepository userRepository;

    public ApiResponse save(BankDTO dto) {
        Bank bank = new Bank();
        bank.setName(dto.getName());
        bank.setAddress(dto.getAddress());

        User director=new User();
        director.setFullName(dto.getFullName());
        director.setPassword(passwordEncoder.encode("7777"));

        List<Role> all = roleRepository.findAll();
        director.setRoleList(new LinkedHashSet<>(all));

        bank.setDirector(director);

        bankRepository.save(bank);

        return ApiResponse.builder().message("Added").success(true).build();
    }

    public ApiResponse edit(Integer id, BankDTO dto) {
        Optional<Bank> byId = bankRepository.findById(id);
        if (byId.isEmpty()) return ApiResponse.builder().message("Bank not found").success(false).build();

        Bank bank = byId.get();
        bank.setName(dto.getName());
        bank.setAddress(dto.getAddress());

        User director = bank.getDirector();
        director.setFullName(dto.getFullName());

        bankRepository.save(bank);

        return ApiResponse.builder().message("Edited").success(true).build();
    }
}
