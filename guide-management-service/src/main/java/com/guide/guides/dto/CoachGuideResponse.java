package com.guide.guides.dto;

import java.util.List;

import com.guide.guides.model.Coach;

import lombok.Data;

@Data
public class CoachGuideResponse {
    List<GuideResponse> guides;
    Coach coach;
}
