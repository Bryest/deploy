package com.game_coin.payments.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDetailGameCoinRequest {
    List<DetailGameCoinRequest> lCoinRequests;
    private Long userId;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", shape = Shape.STRING)
    private Date saleOrder;
}
