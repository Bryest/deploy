package com.guide.guides.service;

import java.util.List;

import com.guide.guides.dto.CategoryRequest;
import com.guide.guides.dto.CategoryResponse;

public interface CategoryService {
    List<CategoryResponse> getAll();

    List<CategoryResponse> getAllByGameId(Long id);

    CategoryResponse getById(Long id);

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(CategoryRequest request, Long id);

    void delete(Long id);
}
