package com.game.games.dto;

import lombok.Data;

@Data
public class GameResponse {
    private Long id;

    private String name;

    private String subtitle;

    private String age;

    private String description;

    private String image;

    private String background;
}
