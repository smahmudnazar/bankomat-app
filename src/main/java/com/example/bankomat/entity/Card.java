package com.example.bankomat.entity;

import com.example.bankomat.entity.enums.CardTypeEnum;
import com.example.bankomat.entity.enums.PermissionEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true,nullable = false)
    @Length(min = 15,max = 16)
    private String code;

    @ManyToOne
    private Bank bank;

    @Column(nullable = false)
    @Length(min = 2,max = 3)
    private String CVV;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String password;

    private Date expiredDate;

    private Date dueDate;

    @Enumerated(EnumType.STRING)
    private CardTypeEnum cardType;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<PermissionEnum> permissionEnum;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        for (PermissionEnum permissionEnum : this.getPermissionEnum()) {
            authorities.add(new SimpleGrantedAuthority(permissionEnum.name()));
        }
        return authorities;
    }

    public Card(String code, Bank bank, String CVV, String fullName, String password, Date expiredDate, Date dueDate, CardTypeEnum cardType, List<PermissionEnum> permissionEnum) {
        this.code = code;
        this.bank = bank;
        this.CVV = CVV;
        this.fullName = fullName;
        this.password = password;
        this.expiredDate = expiredDate;
        this.dueDate = dueDate;
        this.cardType = cardType;
        this.permissionEnum = permissionEnum;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
