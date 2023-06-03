package com.game_coin.payments.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class OrderDetailGameCoinId implements Serializable {
    private Long orderId;

    @Column(name = "game_coin_id")
    private Long gameCoinId;
}
