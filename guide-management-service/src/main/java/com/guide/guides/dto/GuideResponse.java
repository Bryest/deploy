package com.guide.guides.dto;

import com.guide.guides.model.Coach;
import com.guide.guides.model.Game;

import lombok.Data;

@Data
public class GuideResponse {
    private Long id;

    private String title;

    private String description;

    private Long coachId;

    private CategoryResponse category;

    private Long gameId;

    private Long points;

    private Double descount;

    private Game game;

    private Coach coach;
}
