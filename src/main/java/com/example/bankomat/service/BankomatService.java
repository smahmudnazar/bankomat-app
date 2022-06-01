package com.example.bankomat.service;

import com.example.bankomat.dto.ApiResponse;
import com.example.bankomat.dto.BankomatDTO;
import com.example.bankomat.dto.MoneyDTO;
import com.example.bankomat.entity.Bank;
import com.example.bankomat.entity.Bankomat;
import com.example.bankomat.entity.Money;
import com.example.bankomat.entity.enums.MoneyType;
import com.example.bankomat.entity.enums.CardTypeEnum;
import com.example.bankomat.repository.BankRepository;
import com.example.bankomat.repository.BankomatRepository;
import com.example.bankomat.repository.MoneyRepository;
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
    final MoneyRepository moneyRepository;

    public ApiResponse save(BankomatDTO dto) {
        Optional<Bank> optionalBank = bankRepository.findById(dto.getBank_id());
        if (optionalBank.isEmpty()) return ApiResponse.builder().message("Bank not found").success(false).build();


        Bankomat bankomat = new Bankomat();
        bankomat.setBank(optionalBank.get());
        bankomat.setAddress(dto.getAddress());
        bankomat.setCardType(CardTypeEnum.valueOf(dto.getCardType()));

        List<Money> banknotList = new ArrayList<>();
        for (MoneyDTO banknoteDTO : dto.getBanknotes()) {
            Money banknot = new Money();
            banknot.setBanknoteType(MoneyType.valueOf(banknoteDTO.getBanknoteType()));
            banknot.setQuantity(banknoteDTO.getQuantity());
            banknot.setSumma((double) (banknot.getBanknoteType().getValue() * banknot.getQuantity()));

            banknotList.add(banknot);
        }
        if (bankomat.getMax() < getSum(banknotList))
            return ApiResponse.builder().success(false).message("Siz kiritgan summa judayam ko'p").build();

        bankomat.setMoneyList(banknotList);

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

//        List<Money> banknotList = new ArrayList<>();
        List<Money> banknotList = bankomat.getMoneyList();
        for (MoneyDTO banknoteDTO : dto.getBanknotes()) {
//            Money banknot = new Money();

            for (Money money : banknotList) {
                Optional<Money> optionalMoney = moneyRepository.findById(money.getId());
                if (optionalMoney.isEmpty()) return ApiResponse.builder().message("Money not found").success(false).build();

                Money banknot = optionalMoney.get();
                banknot.setBanknoteType(MoneyType.valueOf(banknoteDTO.getBanknoteType()));
                banknot.setQuantity(banknoteDTO.getQuantity());
                banknot.setSumma((double) (banknot.getBanknoteType().getValue() * banknot.getQuantity()));

                banknotList.add(banknot);
            }


        }

        if (bankomat.getMax() < getSum(banknotList))
            return ApiResponse.builder().success(false).message("Siz kiritgan summa judayam ko'p").build();
        bankomat.setMoneyList(banknotList);

        bankomat.setBalance(getSum(banknotList));

        bankomatRepository.save(bankomat);

        return ApiResponse.builder().message("Edited").success(true).build();
    }


    public Double getSum(List<Money> banknotList) {
        double summa = 0;
        for (Money banknot : banknotList) {
            summa += banknot.getSumma();
        }
        return summa;
    }
}
