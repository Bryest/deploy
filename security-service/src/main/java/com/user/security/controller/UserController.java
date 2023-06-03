package com.user.security.controller;

import java.util.List;

import com.user.security.dto.AuthenticateRequest;
import com.user.security.dto.UserRequest;
import com.user.security.dto.UserResponse;
import com.user.security.service.UserService;

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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    private ResponseEntity<List<UserResponse>> getAll() {
        var response = userService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<UserResponse> geById(@PathVariable("id") Long id) {
        var response = userService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    private ResponseEntity<UserResponse> authenticateUser(@RequestBody AuthenticateRequest request) {
        var response = userService.getByUsernameAndPassword(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
        var response = userService.create(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<UserResponse> update(@RequestBody UserRequest request, @PathVariable("id") Long id) {
        var response = userService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/points")
    private ResponseEntity<UserResponse> updateWallet(@RequestParam(value = "points") Long points, @PathVariable("id") Long id) {
        var response = userService.updateWallet(points, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
