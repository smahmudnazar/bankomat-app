package com.example.bankomat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BanknotDTO {
    private String banknoteType;
    private Integer quantity;

}
