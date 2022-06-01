package com.example.bankomat.controller;

import com.example.bankomat.dto.LoginCardDTO;
import com.example.bankomat.dto.LoginDTO;
//import com.example.bankomat.security.JwtFilterCard;
import com.example.bankomat.security.JwtProvider;
import com.example.bankomat.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    final JwtProvider jwtProvider;
    final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        UserDetails userDetails = authService.loadUserByUsername(loginDTO.getName());
        String token = jwtProvider.generateToken(loginDTO.getName());

        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/loginCard")
    public ResponseEntity<?> loginCard(@RequestBody LoginCardDTO dto) {
        UserDetails userDetails = authService.loadUserByUsername(dto.getCode());
        String token = jwtProvider.generateToken(dto.getCode());


        return ResponseEntity.ok().body(token);
    }


}
