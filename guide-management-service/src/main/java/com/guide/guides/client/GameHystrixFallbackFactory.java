package com.guide.guides.client;

import com.guide.guides.model.Game;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class GameHystrixFallbackFactory implements GameClient {

    @Override
    public ResponseEntity<Game> getById(Long id) {
        Game game = new Game();

        game.setId(null);
        game.setAge("undefined");
        game.setDescription("undefined");
        game.setImage("undefined");
        game.setName("undefined");
        game.setSubtitle("undefined");

        return new ResponseEntity<>(game, HttpStatus.OK);
    }

}
