package com.game_coin.payments.controller;

import java.util.List;

import com.game_coin.payments.dto.GameCoinOfferRequest;
import com.game_coin.payments.dto.GameCoinOfferResponse;
import com.game_coin.payments.service.GameCoinOfferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game-coin-offers")
public class GameCoinOfferController {

    @Autowired
    private GameCoinOfferService gameCoinOfferService;

    @GetMapping
    private ResponseEntity<List<GameCoinOfferResponse>> getAll() {
        var response = gameCoinOfferService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<GameCoinOfferResponse> getById(@PathVariable("id") Long id) {
        var response = gameCoinOfferService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<GameCoinOfferResponse> create(@RequestBody GameCoinOfferRequest request) {
        var response = gameCoinOfferService.create(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<GameCoinOfferResponse> update(@RequestBody GameCoinOfferRequest request,
            @PathVariable("id") Long id) {
        var response = gameCoinOfferService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Long id) {
        gameCoinOfferService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
