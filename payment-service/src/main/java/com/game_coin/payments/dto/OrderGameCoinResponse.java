package com.game_coin.payments.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class OrderGameCoinResponse {
    private Long id;
    private Long customerId;
    private Long totalPrice;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date saleOrder;
}