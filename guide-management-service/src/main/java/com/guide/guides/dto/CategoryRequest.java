package com.guide.guides.dto;

import lombok.Data;

@Data
public class CategoryRequest {
    private String name;

    private Long gameId;
}
