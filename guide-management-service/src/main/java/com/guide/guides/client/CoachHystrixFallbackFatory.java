package com.guide.guides.client;

import com.guide.guides.model.Coach;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CoachHystrixFallbackFatory implements CoachClient {

    @Override
    public ResponseEntity<Coach> geById(Long id) {
        Coach coach = new Coach();
        coach.setId(null);
        coach.setGameId(null);
        coach.setLastName("undefined");
        coach.setName("undefined");
        coach.setNameCoach("undefined");
        return new ResponseEntity<>(coach, HttpStatus.OK);
    }

}
