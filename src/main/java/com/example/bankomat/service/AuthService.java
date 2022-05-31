package com.example.bankomat.service;


import com.example.bankomat.entity.Card;
import com.example.bankomat.entity.User;
import com.example.bankomat.repository.CardRepository;
import com.example.bankomat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    final UserRepository userRepository;
    final CardRepository cardRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByFullName(username);
        Optional<Card> byCode = cardRepository.findByCode(username);

        if (byCode.isEmpty())  return byUsername.orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return byCode.orElseThrow(() -> new UsernameNotFoundException("Card not found!"));

    }
}