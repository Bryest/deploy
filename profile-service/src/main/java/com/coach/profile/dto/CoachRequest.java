package com.coach.profile.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class CoachRequest {

    private String username;

    private String password;

    private String email;

    private String description;

    private String name;

    private String number;

    private String lastName;

    private String nameCoach;

    @JsonFormat(pattern = "yyyy/MM/dd", shape = Shape.STRING)
    private Date createdDate;

    @JsonFormat(pattern = "yyyy/MM/dd", shape = Shape.STRING)
    private Date birthday;

    private Long gameId;
}
