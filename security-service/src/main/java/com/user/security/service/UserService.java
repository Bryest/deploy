package com.user.security.service;

import java.util.List;

import com.user.security.dto.AuthenticateRequest;
import com.user.security.dto.UserRequest;
import com.user.security.dto.UserResponse;

public interface UserService {
    List<UserResponse> getAll();

    UserResponse getById(Long id);

    UserResponse getByUsernameAndPassword(AuthenticateRequest request);

    UserResponse create(UserRequest request);

    UserResponse update(UserRequest request, Long id);

    UserResponse updateWallet(Long points, Long id);

    void delete(Long id);
}
