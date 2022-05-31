package com.example.bankomat.service;

import com.example.bankomat.dto.ApiResponse;
import com.example.bankomat.dto.UserDTO;
import com.example.bankomat.entity.Role;
import com.example.bankomat.entity.User;
import com.example.bankomat.entity.enums.PermissionEnum;
import com.example.bankomat.entity.enums.RoleEnum;
import com.example.bankomat.repository.RoleRepository;
import com.example.bankomat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final RoleRepository roleRepository;

    public ApiResponse add(UserDTO dto) {
        User user =new User();
        user.setFullName(dto.getFullName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

//        Set<Role> roles=new LinkedHashSet<>();
//        Role moderRole = roleRepository.save(new Role(RoleEnum.EMPLOYEE,
//                Arrays.asList(
//                        PermissionEnum.CARD_CRUD,
//                        PermissionEnum.BANKOMAT_CRUD,
//                        PermissionEnum.READ_USER)));
//        roles.add(moderRole);

        Optional<Role> byRoleName = roleRepository.findByRoleName(RoleEnum.EMPLOYEE);
        user.setRoleList(new LinkedHashSet(Collections.singleton(byRoleName.get())));

//        user.setRoleList(new LinkedHashSet<>(roles));
        userRepository.save(user);

        return ApiResponse.builder().message("Saved").success(true).build();
    }

    public ApiResponse edit(Integer id, UserDTO dto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) return ApiResponse.builder().message("User not found").success(false).build();

        User user = optionalUser.get();
        user.setFullName(dto.getFullName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);

        return ApiResponse.builder().success(true).message("Edited").build();
    }
}
