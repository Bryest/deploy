package com.game.games.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.game.exception.ResourceNotFoundExceptionRequest;
import com.game.games.dto.GameRequest;
import com.game.games.dto.GameResponse;
import com.game.games.entity.Game;
import com.game.games.repository.GameRepository;
import com.game.games.service.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    private GameResponse convertToResponse(Game game) {
        GameResponse response = new GameResponse();
        response.setAge(game.getAge());
        response.setDescription(game.getDescription());
        response.setId(game.getId());
        response.setImage(game.getImage());
        response.setName(game.getName());
        response.setSubtitle(game.getSubtitle());
        response.setBackground(game.getBackground());

        return response;
    }

    private Game convertToEntity(GameRequest request) {
        Game game = new Game();
        game.setAge(request.getAge());
        game.setDescription(request.getDescription());
        game.setImage(request.getImage());
        game.setName(request.getName());
        game.setSubtitle(request.getSubtitle());
        game.setBackground(request.getBackground());

        return game;
    }

    private Game convertToEntity(GameRequest request, Long id) {
        Game game = new Game();
        game.setAge(request.getAge());
        game.setDescription(request.getDescription());
        game.setImage(request.getImage());
        game.setName(request.getName());
        game.setId(id);
        game.setSubtitle(request.getSubtitle());
        game.setBackground(request.getBackground());

        return game;
    }

    @Override
    public List<GameResponse> getAll() {
        var entities = gameRepository.findAll();
        var response = entities.stream().map(entity -> convertToResponse(entity)).collect(Collectors.toList());
        return response;
    }

    @Override
    public GameResponse getById(Long id) {
        var entity = gameRepository.getGameById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Game not found"));
        var response = convertToResponse(entity);
        return response;
    }

    @Override
    public GameResponse create(GameRequest request) {

        var entity = convertToEntity(request);

        try {
            gameRepository.save(entity);
            var response = convertToResponse(entity);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating game");
        }
    }

    @Override
    public GameResponse update(GameRequest request, Long id) {
        var entity = gameRepository.getGameById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Game not found"));

        entity = convertToEntity(request, id);

        try {
            gameRepository.save(entity);
            var response = convertToResponse(entity);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating game");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            gameRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting game");
        }
    }

}
