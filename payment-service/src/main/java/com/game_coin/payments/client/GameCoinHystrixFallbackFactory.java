package com.game_coin.payments.client;

import com.game_coin.payments.model.GameCoin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class GameCoinHystrixFallbackFactory implements GameCoinClient {

    @Override
    public ResponseEntity<GameCoin> getById(Long id) {
        GameCoin gameCoin = new GameCoin();
        gameCoin.setAvaible(false);
        gameCoin.setDescription("undefined");
        gameCoin.setId(null);
        gameCoin.setName("undefined");
        gameCoin.setPrice(0.0);
        return new ResponseEntity<>(gameCoin, HttpStatus.OK);
    }

}
