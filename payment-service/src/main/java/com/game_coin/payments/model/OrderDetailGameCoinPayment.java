package com.game_coin.payments.model;

import lombok.Data;

import java.util.List;

@Data
public class OrderDetailGameCoinPayment {
    private List<DetailGameCoinResponse> lCoinResponses;
    private Long userId;
    private OrderGameCoinResponse orderGameCoinResponse;
    private Double total;
    private User user;
}