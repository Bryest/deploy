package com.coach.profile.controller;

import java.util.List;

import com.coach.profile.dto.AuthenticateRequest;
import com.coach.profile.dto.CoachRequest;
import com.coach.profile.dto.CoachResponse;
import com.coach.profile.dto.CoachResponseSimple;
import com.coach.profile.service.CoachService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coaches")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @GetMapping
    private ResponseEntity<List<CoachResponse>> getAll() {
        var response = coachService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/game/{id}")
    private ResponseEntity<List<CoachResponseSimple>> getAllByGameId(@PathVariable("id") Long id) {
        var response = coachService.getAllByGameId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<CoachResponse> geById(@PathVariable("id") Long id) {
        var response = coachService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    private ResponseEntity<CoachResponse> authenticateUser(@RequestBody AuthenticateRequest request) {
        var response = coachService.getByUsernameAndPassword(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<CoachResponse> create(@RequestBody CoachRequest request) {
        var response = coachService.create(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<CoachResponse> update(@RequestBody CoachRequest request, @PathVariable("id") Long id) {
        var response = coachService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/points")
    private ResponseEntity<CoachResponse> updateWallet(@PathVariable("id") Long id,
            @RequestParam(name = "point", required = true) Long point) {
        var response = coachService.updateWallet(id, point);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Long id) {
        coachService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
