package com.example.bankomat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankomatDTO {
    private String cardType,address;
    private Integer bank_id;

    private List<MoneyDTO> banknotes;

}
