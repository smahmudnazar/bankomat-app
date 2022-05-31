package com.example.bankomat.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name,address;

    @OneToOne(cascade = CascadeType.ALL)
    private User director;

    public Bank(String name, String address, User director) {
        this.name = name;
        this.address = address;
        this.director = director;
    }
}
