package com.game_coin.payments.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tb_order_detail_game_coins")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailGameCoin {

    @EmbeddedId
    private OrderDetailGameCoinId orderDetailGameCoinId = new OrderDetailGameCoinId();

    private Long quantify;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_game_id")
    private OrderGameCoin orderGameCoin;

    @Column(name = "subtotal")
    private Double subtotal;
}
