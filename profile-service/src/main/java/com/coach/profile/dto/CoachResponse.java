package com.coach.profile.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CoachResponse {
    private Long id;

    private Long points;

    private String username;

    private String nameCoach;

    private String description;

    private String password;

    private String email;

    private String name;

    private String number;

    private String lastName;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date createdDate;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date birthday;

    private Long gameId;
}
