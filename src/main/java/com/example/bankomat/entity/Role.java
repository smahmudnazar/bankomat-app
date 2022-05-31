package com.example.bankomat.entity;

import com.example.bankomat.entity.enums.PermissionEnum;
import com.example.bankomat.entity.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleEnum roleName;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<PermissionEnum> permissionEnum;

    public Role(RoleEnum roleName, List<PermissionEnum> permissionEnum) {
        this.roleName = roleName;
        this.permissionEnum = permissionEnum;
    }
}
