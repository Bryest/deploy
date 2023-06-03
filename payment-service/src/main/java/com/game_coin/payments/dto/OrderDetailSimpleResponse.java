package com.game_coin.payments.dto;

import lombok.Data;

@Data
public class OrderDetailSimpleResponse {
    private Long orderId;
    private Long gameCoinId;
    private Long quantify;
    private Long customerId;
    private Double subtotal;
}
