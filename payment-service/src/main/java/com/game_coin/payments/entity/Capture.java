package com.game_coin.payments.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_capture")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Capture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private Date date;

    private String token;

    private String payerId;

    private String paymentId;

}
