package com.game_coin.payments.dto;

import com.game_coin.payments.model.User;
import lombok.Data;

import java.util.List;

@Data
public class OrdeDetailGameCoinPayment {
    List<DetailGameCoinResponse> lCoinResponses;
    private Long userId;
    private OrderGameCoinResponse orderGameCoinResponse;
    private Double total;
    private User user;
}
