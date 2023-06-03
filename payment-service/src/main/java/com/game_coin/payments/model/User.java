package com.game_coin.payments.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;
    private Long points;
    private String name;
    private String lastName;
    private String email;
    @JsonFormat(pattern = "yyyy/MM/dd", shape = Shape.STRING)
    private Date createdDate;
}
