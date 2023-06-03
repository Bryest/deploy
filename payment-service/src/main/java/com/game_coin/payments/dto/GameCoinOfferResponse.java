package com.game_coin.payments.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class GameCoinOfferResponse {
    private Long id;
    private String image;
    private String name;
    private String description;
    private Long point;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", shape = Shape.STRING)
    private Date createdAt;
    private Double price;
    private Boolean avaible;
}
