package com.game_coin.payments.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_game_coin")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GameCoinOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String image;

    private Boolean avaible;

    private String name;

    private String description;

    private Long point;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date createdAt;

    private Double price;
}