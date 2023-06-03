package com.game_coin.payments.controller;

import com.game_coin.payments.dto.DetailCardRequest;
import com.game_coin.payments.dto.DetailCardResponse;
import com.game_coin.payments.dto.UserDetailCardResponse;
import com.game_coin.payments.service.DetailCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detail-cards")
public class DetailCardController {

    @Autowired
    private DetailCardService cardService;

    @GetMapping
    private ResponseEntity<List<DetailCardResponse>> getAll() {
        var response = cardService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    private ResponseEntity<UserDetailCardResponse> getAllByUserId(@PathVariable("id") Long id) {
        var response = cardService.getAllByUserId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<DetailCardResponse> create(@RequestBody DetailCardRequest request) {
        var response = cardService.create(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<DetailCardResponse> getById(@RequestBody DetailCardRequest request,
            @PathVariable("id") Long id) {
        var response = cardService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> getById(@PathVariable("id") Long id) {
        cardService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
