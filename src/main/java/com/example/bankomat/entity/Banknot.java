package com.example.bankomat.entity;

import com.example.bankomat.entity.enums.BanknoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Banknot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private BanknoteType banknoteType;

    private Integer quantity;

    private Double summa;
}
