package com.game_coin.payments.client;

import com.game_coin.payments.model.GameCoin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "payment-service", path = "/game-coin-offers", fallback = GameCoinHystrixFallbackFactory.class)
public interface GameCoinClient {
    @GetMapping("/{id}")
    public ResponseEntity<GameCoin> getById(@PathVariable("id") Long id);
}
