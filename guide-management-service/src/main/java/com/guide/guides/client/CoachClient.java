package com.guide.guides.client;

import com.guide.guides.model.Coach;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profile-service", path = "/coaches", fallback = CoachHystrixFallbackFatory.class)
public interface CoachClient {
    @GetMapping("/{id}")
    public ResponseEntity<Coach> geById(@PathVariable("id") Long id);
}
