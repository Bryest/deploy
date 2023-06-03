package com.user.security.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;

    private Long points;

    private String username;

    private String password;

    private String email;

    private String name;

    private String number;

    private String lastName;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date createdDate;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date birthday;

    private Long gameFavorite1;

    private Long gameFavorite2;

    private Long gameFavorite3;
}
