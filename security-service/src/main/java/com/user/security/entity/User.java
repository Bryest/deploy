package com.user.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long points;

    private String username;

    private String password;

    private String email;

    private String name;

    private String number;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "created_date")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date createdDate;

    @Column(name = "birthday")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date birthday;

    @Column(name = "game_favorite_1")
    private Long gameFavorite1;

    @Column(name = "game_favorite_2")
    private Long gameFavorite2;

    @Column(name = "game_favorite_3")
    private Long gameFavorite3;
}
