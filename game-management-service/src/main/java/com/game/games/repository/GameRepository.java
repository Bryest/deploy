package com.game.games.repository;

import java.util.Optional;

import com.game.games.entity.Game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> getGameById(Long id);
}
