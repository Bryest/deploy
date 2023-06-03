package com.game_coin.payments.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tb_detailcards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long month;

    private Long day;

    private Long code;

    private Long obfuscateCard;

    private Long userId;

    private String cardholder;
}
