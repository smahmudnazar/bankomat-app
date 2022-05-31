package com.example.bankomat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardDTO {
    private Integer bank_id;
    private String fullName;
    private String password;
    private String card_type;

}
