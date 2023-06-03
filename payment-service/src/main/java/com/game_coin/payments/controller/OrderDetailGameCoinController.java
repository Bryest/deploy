package com.game_coin.payments.controller;

import com.game_coin.payments.dto.OrdeDetailGameCoinPayment;
import com.game_coin.payments.dto.OrderDetailGameCoinRequest;
import com.game_coin.payments.dto.OrderDetailGameCoinResponse;
import com.game_coin.payments.dto.OrderDetailSimpleResponse;
import com.game_coin.payments.service.OrderDetailGameCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-detail-game-coins")
public class OrderDetailGameCoinController {

    @Autowired
    private OrderDetailGameCoinService orderDetailGameCoinService;

    @GetMapping
    private ResponseEntity<List<OrderDetailSimpleResponse>> getAll() {
        var response = orderDetailGameCoinService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    private ResponseEntity<OrderDetailGameCoinResponse> getAllByOrderId(@PathVariable("id") Long id) {
        var response = orderDetailGameCoinService.getAllByOrderId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<OrdeDetailGameCoinPayment> create(@RequestBody OrderDetailGameCoinRequest request) {
        var response = orderDetailGameCoinService.create(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Long id) {
        orderDetailGameCoinService.deleteByOrderId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
