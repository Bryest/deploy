package com.guide.guides.dto;

import lombok.Data;

@Data
public class GuideRequest {
    private String title;

    private String description;

    private Long coachId;

    private Long categoryId;

    private Long gameId;

    private Long points;

    private Double descount;
}
