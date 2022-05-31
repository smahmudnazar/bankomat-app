package com.example.bankomat.controller;

import com.example.bankomat.dto.ApiResponse;
import com.example.bankomat.dto.BankDTO;
import com.example.bankomat.dto.UserDTO;
import com.example.bankomat.entity.User;
import com.example.bankomat.repository.UserRepository;
import com.example.bankomat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    final UserRepository userRepository;
    final UserService userService;

    @PreAuthorize("hasAnyAuthority('USER_CRUD','READ_USER')")
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(userRepository.findAll());

    }

    @PreAuthorize("hasAnyAuthority('USER_CRUD','READ_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        Optional<User> byId = userRepository.findById(id);

        return ResponseEntity.status(byId.isEmpty() ?
                HttpStatus.NOT_FOUND : HttpStatus.OK).body(byId.orElse(new User()));
    }

    @PreAuthorize("hasAnyAuthority('USER_CRUD','DELETE_USER')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) return ResponseEntity.ok().body("User not Found");

        userRepository.delete(user.get());
        return ResponseEntity.ok().body("Deleted");
    }

    @PreAuthorize("hasAnyAuthority('USER_CRUD','ADD_USER')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody UserDTO dto) {
        ApiResponse apiResponse = userService.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }

    @PreAuthorize("hasAnyAuthority('USER_CRUD','EDIT_USER')")
    @PutMapping
    public ResponseEntity<?> edit(@PathVariable Integer id,@RequestBody UserDTO dto){
        ApiResponse apiResponse = userService.edit(id, dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }

}
