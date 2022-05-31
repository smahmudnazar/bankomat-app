package com.example.bankomat.entity.enums;


public enum BanknoteType {
    MING_SOM(1000),
    IKKI_MING_SOM(2000),
    BESH_MING_SOM(5000),
    ON_MING_SOM(10000),
    YIGIRMA_MING_SOM(20000),
    ELLIK_MING_SOM(50000),
    YUZ_MING_SOM(100000),
    ONE_DOLLAR(1),
    TWO_DOLLAR(2),
    FIVE_DOLLAR(5),
    TEN_DOLLAR(10),
    TWENTY_DOLLAR(20),
    FIFTY_DOLLAR(50),
    ONE_HUNDRED_DOLLAR(100);

    private Integer value;

    BanknoteType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
