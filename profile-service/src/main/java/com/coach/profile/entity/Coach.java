package com.coach.profile.entity;

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
@Table(name = "tb_coach")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long points;

    private String username;

    @Column(name = "name_coach")
    private String nameCoach;

    @Lob
    private String description;

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

    @Column(name = "game_id")
    private Long gameId;
}
