package com.game_coin.payments.dto;

import com.game_coin.payments.model.User;
import lombok.Data;

@Data
public class DetailCardResponse {
    private Long id;
    private Long month;
    private Long day;
    private String cardholder;
    private Long userId;
    private Long obfuscatedCard;
    private Long code;
    private User user;
}
