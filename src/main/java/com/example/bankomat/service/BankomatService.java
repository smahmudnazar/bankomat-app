package com.example.bankomat.service;

import com.example.bankomat.dto.ApiResponse;
import com.example.bankomat.dto.BanknotDTO;
import com.example.bankomat.dto.BankomatDTO;
import com.example.bankomat.entity.Bank;
import com.example.bankomat.entity.Banknot;
import com.example.bankomat.entity.Bankomat;
import com.example.bankomat.entity.enums.BanknoteType;
import com.example.bankomat.entity.enums.CardTypeEnum;
import com.example.bankomat.repository.BankRepository;
import com.example.bankomat.repository.BankomatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankomatService {
    final BankomatRepository bankomatRepository;
    final BankRepository bankRepository;

    public ApiResponse save(BankomatDTO dto) {
        Optional<Bank> optionalBank = bankRepository.findById(dto.getBank_id());
        if (optionalBank.isEmpty()) return ApiResponse.builder().message("Bank not found").success(false).build();


        Bankomat bankomat = new Bankomat();
        bankomat.setBank(optionalBank.get());
        bankomat.setAddress(dto.getAddress());
        bankomat.setCardType(CardTypeEnum.valueOf(dto.getCardType()));

        List<Banknot> banknotList = new ArrayList<>();
        for (BanknotDTO banknoteDTO : dto.getBanknotes()) {
            Banknot banknot = new Banknot();
            banknot.setBanknoteType(BanknoteType.valueOf(banknoteDTO.getBanknoteType()));
            banknot.setQuantity(banknoteDTO.getQuantity());
            banknot.setSumma((double) (banknot.getBanknoteType().getValue() * banknot.getQuantity()));

            banknotList.add(banknot);
        }
        if (bankomat.getMax() < getSum(banknotList))
            return ApiResponse.builder().success(false).message("Siz kiritgan summa judayam ko'p").build();

        bankomat.setBanknoteList(banknotList);

        bankomat.setBalance(getSum(banknotList));

        bankomatRepository.save(bankomat);

        return ApiResponse.builder().message("Added").success(true).build();
    }


    public ApiResponse edit(BankomatDTO dto, Integer id) {
        Optional<Bankomat> bankomatOptional = bankomatRepository.findById(id);
        if (bankomatOptional.isEmpty())
            return ApiResponse.builder().success(false).message("Bankomat not found").build();

        Bankomat bankomat = bankomatOptional.get();

        bankomat.setAddress(dto.getAddress());
        bankomat.setCardType(CardTypeEnum.valueOf(dto.getCardType()));

        List<Banknot> banknotList = new ArrayList<>();
        for (BanknotDTO banknoteDTO : dto.getBanknotes()) {
            Banknot banknot = new Banknot();
            banknot.setBanknoteType(BanknoteType.valueOf(banknoteDTO.getBanknoteType()));
            banknot.setQuantity(banknoteDTO.getQuantity());
            banknot.setSumma((double) (banknot.getBanknoteType().getValue() * banknot.getQuantity()));

            banknotList.add(banknot);
        }

        if (bankomat.getMax() < getSum(banknotList))
            return ApiResponse.builder().success(false).message("Siz kiritgan summa judayam ko'p").build();
        bankomat.setBanknoteList(banknotList);

        bankomat.setBalance(getSum(banknotList));

        bankomatRepository.save(bankomat);

        return ApiResponse.builder().message("Edited").success(true).build();
    }


    public Double getSum(List<Banknot> banknotList) {
        double summa = 0;
        for (Banknot banknot : banknotList) {
            summa += banknot.getSumma();
        }
        return summa;
    }
}
