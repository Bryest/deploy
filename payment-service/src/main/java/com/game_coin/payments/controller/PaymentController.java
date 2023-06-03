package com.game_coin.payments.controller;

import com.game_coin.exception.ResourceNotFoundExceptionRequest;
import com.game_coin.payments.dto.OrderDetailGameCoinRequest;
import com.game_coin.payments.service.impl.PaymentService;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    private ResponseEntity<?> create(@RequestBody OrderDetailGameCoinRequest request) {
        try {
            var entity = paymentService.authorizePayment(request);
            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (PayPalRESTException ex) {
            ex.printStackTrace();
            throw new ResourceNotFoundExceptionRequest("Error ocurred whit paypal");
        }
    }

}
