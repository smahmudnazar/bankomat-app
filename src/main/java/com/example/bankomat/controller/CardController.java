package com.example.bankomat.controller;

import com.example.bankomat.dto.ApiResponse;
import com.example.bankomat.dto.CardDTO;
import com.example.bankomat.dto.SetPasswordDTO;
import com.example.bankomat.entity.Card;
import com.example.bankomat.repository.CardRepository;
import com.example.bankomat.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card")
public class CardController {
    final CardRepository cardRepository;
    final CardService cardService;

    @PreAuthorize("hasAnyAuthority('CARD_CRUD','READ_CARD')")
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(cardRepository.findAll());
    }


    @PreAuthorize("hasAnyAuthority('CARD_CRUD','READ_CARD')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        Optional<Card> byId = cardRepository.findById(id);

        return ResponseEntity.status(byId.isEmpty() ?
                HttpStatus.NOT_FOUND : HttpStatus.OK).body(byId.orElse(new Card()));
    }


    @PreAuthorize("hasAnyAuthority('CARD_CRUD','DELETE_CARD')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<Card> card = cardRepository.findById(id);
        if (card.isEmpty()) return ResponseEntity.ok().body("Card not Found");

        cardRepository.delete(card.get());
        return ResponseEntity.ok().body("Deleted");
    }


    @PreAuthorize("hasAnyAuthority('CARD_CRUD','ADD_CARD')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody CardDTO dto) {
        ApiResponse apiResponse = cardService.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @PreAuthorize("hasAnyAuthority('CARD_CRUD','EDIT_CARD')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> edit(@RequestBody SetPasswordDTO password, @PathVariable Integer id) {
        ApiResponse apiResponse = cardService.edit(id, password);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

}
