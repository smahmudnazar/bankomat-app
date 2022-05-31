package com.example.bankomat.entity;

import com.example.bankomat.entity.enums.CardTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bankomat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private CardTypeEnum cardType;

    @ManyToOne
    private Bank bank;


    @OneToMany(cascade = CascadeType.ALL)
    private List<Banknot> banknoteList;

    private Double max=1000000000d;

    private Double balance;

    private String address;

    private Integer ourBankType=0;

    private Integer otherBankType=1;

    private Double min=100000d;

    private boolean block=false;



}
