package com.game.games.service;

import java.util.List;

import com.game.games.dto.GameRequest;
import com.game.games.dto.GameResponse;

public interface GameService {
    List<GameResponse> getAll();

    GameResponse getById(Long id);

    GameResponse create(GameRequest request);

    GameResponse update(GameRequest request, Long id);

    void delete(Long id);
}
