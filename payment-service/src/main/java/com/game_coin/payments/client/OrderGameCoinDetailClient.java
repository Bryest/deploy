package com.game_coin.payments.client;

import com.game_coin.payments.dto.OrderDetailGameCoinRequest;
import com.game_coin.payments.model.OrderDetailGameCoinPayment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "payment-service", path = "/order-detail-game-coins", fallback = OrderGameCoinDetailHystrixFallbackFactory.class)
public interface OrderGameCoinDetailClient {
    @GetMapping("/order/{id}")
    public ResponseEntity<OrderDetailGameCoinPayment> getAllByOrderId(@PathVariable("id") Long id);

    @PostMapping
    public ResponseEntity<OrderDetailGameCoinPayment> create(@RequestBody OrderDetailGameCoinRequest request);

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id);
}
