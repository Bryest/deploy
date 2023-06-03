package com.guide.guides.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.guide.exception.ResourceNotFoundExceptionRequest;
import com.guide.guides.client.CoachClient;
import com.guide.guides.client.GameClient;
import com.guide.guides.dto.CategoryResponse;
import com.guide.guides.dto.GuideRequest;
import com.guide.guides.dto.GuideResponse;
import com.guide.guides.dto.CoachGuideResponse;
import com.guide.guides.entity.Category;
import com.guide.guides.entity.Guide;
import com.guide.guides.model.Coach;
import com.guide.guides.repository.CategoryRepository;
import com.guide.guides.repository.GuideRepository;
import com.guide.guides.service.GuideService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GuideServiceImpl implements GuideService {

    @Autowired
    private GuideRepository guideRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Qualifier("com.guide.guides.client.CoachClient")
    @Autowired
    private CoachClient coachClient;

    @Qualifier("com.guide.guides.client.GameClient")
    @Autowired
    private GameClient gameClient;

    private GuideResponse convertToResponse(Guide guide) {
        GuideResponse response = new GuideResponse();
        CategoryResponse category = new CategoryResponse();
        category.setGameId(guide.getCategory().getGameId());
        category.setId(guide.getCategory().getId());
        category.setName(guide.getCategory().getName());

        response.setCategory(category);
        response.setCoachId(guide.getCoachId());
        response.setDescount(guide.getDescount());
        response.setDescription(guide.getDescription());
        response.setGameId(guide.getGameId());
        response.setId(guide.getId());
        response.setPoints(guide.getPoints());
        response.setTitle(guide.getTitle());
        return response;
    }

    private Guide convertToEntity(GuideRequest request, Category category) {
        Guide guide = new Guide();

        guide.setCategory(category);
        guide.setCoachId(request.getCoachId());
        guide.setDescount(request.getDescount());
        guide.setDescription(request.getDescription());
        guide.setGameId(request.getGameId());
        guide.setPoints(request.getPoints());
        guide.setTitle(request.getTitle());

        return guide;
    }

    private Guide convertToEntity(GuideRequest request, Category category, Long id) {
        Guide guide = new Guide();

        guide.setCategory(category);
        guide.setCoachId(request.getCoachId());
        guide.setDescount(request.getDescount());
        guide.setDescription(request.getDescription());
        guide.setGameId(request.getGameId());
        guide.setId(id);
        guide.setPoints(request.getPoints());
        guide.setTitle(request.getTitle());

        return guide;
    }

    @Override
    public List<GuideResponse> getAll() {
        var entities = guideRepository.findAll();
        var response = entities.stream().map(entity -> convertToResponse(entity))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public List<GuideResponse> getAllByCategoryId(Long id) {
        var entities = guideRepository.findAllByCategoryId(id);
        var response = entities.stream().map(entity -> convertToResponse(entity))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public CoachGuideResponse getAllByCoachId(Long id) {
        var entities = guideRepository.findAllByCoachId(id);
        var guides = entities.stream().map(entity -> convertToResponse(entity))
                .collect(Collectors.toList());

        Coach coach = coachClient.geById(id).getBody();
        CoachGuideResponse response = new CoachGuideResponse();
        response.setCoach(coach);
        response.setGuides(guides);
        return response;
    }

    @Override
    public GuideResponse getById(Long id) {
        var entity = guideRepository.getGuideById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Guide not found"));
        var response = convertToResponse(entity);

        var game = gameClient.getById(entity.getGameId()).getBody();

        response.setGame(game);

        return response;
    }

    @Override
    public GuideResponse create(GuideRequest request) {
        var category = categoryRepository.getCategoryById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Category not found"));

        var game = gameClient.getById(request.getGameId()).getBody();
        var coach = coachClient.geById(request.getCoachId()).getBody();

        if(game.getId() == null){
            throw new ResourceNotFoundExceptionRequest("Game not found");
        }

        if(coach.getId() == null){
            throw new ResourceNotFoundExceptionRequest("Coach not found");
        }

        var entity = convertToEntity(request, category);

        try {
            guideRepository.save(entity);
            var response = convertToResponse(entity);
            response.setGame(game);
            response.setCoach(coach);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating guide");
        }
    }

    @Override
    public GuideResponse update(GuideRequest request, Long id) {
        var entity = guideRepository.getGuideById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Guide not found"));
        var category = categoryRepository.getCategoryById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Category not found"));

        entity = convertToEntity(request, category, id);

        var game = gameClient.getById(request.getGameId()).getBody();
        var coach = coachClient.geById(request.getCoachId()).getBody();

        try {
            guideRepository.save(entity);
            var response = convertToResponse(entity);
            response.setGame(game);
            response.setCoach(coach);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating guide");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            guideRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting guide");
        }
    }

}
