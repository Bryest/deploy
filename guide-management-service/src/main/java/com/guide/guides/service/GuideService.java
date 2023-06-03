package com.guide.guides.service;

import java.util.List;

import com.guide.guides.dto.GuideRequest;
import com.guide.guides.dto.GuideResponse;
import com.guide.guides.dto.CoachGuideResponse;

public interface GuideService {
    List<GuideResponse> getAll();

    List<GuideResponse> getAllByCategoryId(Long id);

    CoachGuideResponse getAllByCoachId(Long id);

    GuideResponse getById(Long id);

    GuideResponse create(GuideRequest request);

    GuideResponse update(GuideRequest request, Long id);

    void delete(Long id);
}
