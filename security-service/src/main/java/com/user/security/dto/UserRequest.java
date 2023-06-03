package com.user.security.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class UserRequest {

    private String username;

    private String password;

    private String email;

    private String name;

    private String number;

    private String lastName;

    @JsonFormat(pattern = "yyyy/MM/dd", shape = Shape.STRING)
    private Date createdDate;

    @JsonFormat(pattern = "yyyy/MM/dd", shape = Shape.STRING)
    private Date birthday;

    private Long gameFavorite1;

    private Long gameFavorite2;

    private Long gameFavorite3;
}
