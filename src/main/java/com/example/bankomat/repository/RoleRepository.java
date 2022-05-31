package com.example.bankomat.repository;


import com.example.bankomat.entity.Role;
import com.example.bankomat.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(RoleEnum name);
}
