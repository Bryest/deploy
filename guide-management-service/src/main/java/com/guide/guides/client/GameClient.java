package com.guide.guides.client;

import com.guide.guides.model.Game;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "game-management-service", path = "/games", fallback = GameHystrixFallbackFactory.class)
public interface GameClient {
    @GetMapping("/{id}")
    public ResponseEntity<Game> getById(@PathVariable("id") Long id);
}
