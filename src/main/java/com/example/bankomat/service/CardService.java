package com.example.bankomat.service;

import com.example.bankomat.dto.ApiResponse;
import com.example.bankomat.dto.CardDTO;
import com.example.bankomat.dto.SetPasswordDTO;
import com.example.bankomat.entity.Bank;
import com.example.bankomat.entity.Card;
import com.example.bankomat.entity.enums.CardTypeEnum;
import com.example.bankomat.entity.enums.PermissionEnum;
import com.example.bankomat.repository.BankRepository;
import com.example.bankomat.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {
    final CardRepository cardRepository;
    final BankRepository bankRepository;
    final PasswordEncoder passwordEncoder;

    public ApiResponse add(CardDTO dto) {
        Optional<Bank> optionalBank = bankRepository.findById(dto.getBank_id());
        if (optionalBank.isEmpty()) return ApiResponse.builder().message("Bank not found").success(false).build();

        if (!(dto.getPassword().length() == 4))
            return ApiResponse.builder().success(false).message("Parol uzunligi 4 xonali bo'lishi kerak").build();

        Card card = new Card();
        card.setBank(optionalBank.get());
        card.setDueDate(Date.valueOf(LocalDate.now()));
        card.setExpiredDate(Date.valueOf(LocalDate.now().plusYears(5)));
        card.setCVV(randomCode(100, 999));
        card.setFullName(dto.getFullName());

        card.setPassword(passwordEncoder.encode(dto.getPassword()));

        card.setCardType(CardTypeEnum.valueOf(dto.getCard_type()));

        String code16size = randomCode(1000000000000000l, 9999999999999999l);

        for (Card card1 : cardRepository.findAll()) {
            if (!card1.getCode().equals(code16size)) {
                card.setCode(code16size);
            } else {
                card.setCode(randomCode(1000000000000000l, 9999999999999999l));
            }
        }

        List<PermissionEnum> permissionEnums = new ArrayList<>();
        permissionEnums.add(PermissionEnum.WORK_WITH_ATM);
        card.setPermissionEnum(permissionEnums);

        cardRepository.save(card);

        return ApiResponse.builder().message("Added").success(true).build();
    }

    public ApiResponse edit(Integer id, SetPasswordDTO password) {
        Optional<Card> cardOptional = cardRepository.findById(id);
        if (cardOptional.isEmpty()) return ApiResponse.builder().message("Card not found").success(false).build();

        if (!(password.getPassword().length() == 4))
            return ApiResponse.builder().success(false).message("Parol uzunligi 4 xonali bo'lishi kerak").build();


        Card card = cardOptional.get();
        card.setPassword(passwordEncoder.encode(password.getPassword()));

        cardRepository.save(card);

        return ApiResponse.builder().message("Edited").success(true).build();
    }


    public String randomCode(long max, long min) {
        long b = (long) Math.floor(Math.random() * (max - min + 1) + min);
        return String.valueOf(b);
    }
}
