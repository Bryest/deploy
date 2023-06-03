package com.guide.guides.dto;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;

    private String name;

    private Long gameId;
}
