package com.guide.guides.controller;

import java.util.List;

import com.guide.guides.dto.CoachGuideResponse;
import com.guide.guides.dto.GuideRequest;
import com.guide.guides.dto.GuideResponse;
import com.guide.guides.service.GuideService;

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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guides")
public class GuideController {

    @Autowired
    private GuideService guideService;

    @GetMapping
    private ResponseEntity<List<GuideResponse>> getAll() {
        var response = guideService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    private ResponseEntity<List<GuideResponse>> getAllByCategoryId(@PathVariable("id") Long id) {
        var response = guideService.getAllByCategoryId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/coach/{id}")
    private ResponseEntity<CoachGuideResponse> getAllByCoachId(@PathVariable("id") Long id) {
        var response = guideService.getAllByCoachId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<GuideResponse> getById(@PathVariable("id") Long id) {
        var response = guideService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<GuideResponse> create(@RequestBody GuideRequest request) {
        var response = guideService.create(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<GuideResponse> update(@RequestBody GuideRequest request, @PathVariable("id") Long id) {
        var response = guideService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Long id) {
        guideService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
