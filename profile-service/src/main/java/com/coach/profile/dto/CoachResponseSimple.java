package com.coach.profile.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CoachResponseSimple {
    private String nameCoach;

    private String description;

    private String email;

    private String name;

    private String lastName;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date createdDate;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date birthday;
}
