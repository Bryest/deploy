package com.game_coin.payments.dto;

import com.game_coin.payments.model.GameCoin;
import lombok.Data;

@Data
public class DetailGameCoinResponse {
    private Long quantify;
    private Long gameCoinId;
    private Double subtotal;
    private GameCoin gameCoin;
}
