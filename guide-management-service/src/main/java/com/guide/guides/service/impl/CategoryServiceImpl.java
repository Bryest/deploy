package com.guide.guides.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.guide.exception.ResourceNotFoundExceptionRequest;
import com.guide.guides.dto.CategoryRequest;
import com.guide.guides.dto.CategoryResponse;
import com.guide.guides.entity.Category;
import com.guide.guides.repository.CategoryRepository;
import com.guide.guides.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    private CategoryResponse convertToResponse(Category entity) {
        CategoryResponse response = new CategoryResponse();
        response.setGameId(entity.getGameId());
        response.setId(entity.getId());
        response.setName(entity.getName());
        return response;
    }

    private Category convertToEntity(CategoryRequest request) {
        Category category = new Category();
        category.setGameId(request.getGameId());
        category.setName(request.getName());
        return category;
    }

    private Category convertToEntity(CategoryRequest request, Long id) {
        Category category = new Category();
        category.setId(id);
        category.setName(request.getName());
        category.setGameId(request.getGameId());
        return category;
    }

    @Override
    public List<CategoryResponse> getAll() {
        var entities = repository.findAll();
        var response = entities.stream().map(entity -> convertToResponse(entity))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public List<CategoryResponse> getAllByGameId(Long id) {
        var entities = repository.findAllByGameId(id);
        var response = entities.stream().map(entity -> convertToResponse(entity))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public CategoryResponse getById(Long id) {
        var entity = repository.getCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Category not found by id"));
        var response = convertToResponse(entity);
        return response;
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        var entity = convertToEntity(request);

        try {
            repository.save(entity);
            var response = convertToResponse(entity);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating category");
        }
    }

    @Override
    public CategoryResponse update(CategoryRequest request, Long id) {
        var entity = repository.getCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Category not found by id"));

        entity = convertToEntity(request, id);

        try {
            repository.save(entity);
            var response = convertToResponse(entity);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating category");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting category");
        }
    }

}
