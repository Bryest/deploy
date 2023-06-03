package com.user.security.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.user.exception.ResourceNotFoundExceptionRequest;
import com.user.security.dto.AuthenticateRequest;
import com.user.security.dto.UserRequest;
import com.user.security.dto.UserResponse;
import com.user.security.entity.User;
import com.user.security.repository.UserRepository;
import com.user.security.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<UserResponse> getAll() {
        var entities = userRepository.findAll();
        var response = entities.stream().map(entity -> mapper.map(entity, UserResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public UserResponse getById(Long id) {
        var entity = userRepository.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("User not found by id"));
        var response = mapper.map(entity, UserResponse.class);
        return response;
    }

    @Override
    public UserResponse getByUsernameAndPassword(AuthenticateRequest request) {
        var entity = userRepository.getUserByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("User not found by id"));
        var valid = encoder.matches(request.getPassword(), entity.getPassword());
        if (valid) {
            var response = mapper.map(entity, UserResponse.class);
            return response;
        }
        throw new ResourceNotFoundExceptionRequest("Credentials invalids");
    }

    @Override
    public UserResponse create(UserRequest request) {
        var username = userRepository.getUserByUsername(request.getUsername())
                .orElse(null);
        if (username != null) {
            throw new ResourceNotFoundExceptionRequest("Username not valid");
        }
        var email = userRepository.getUserByEmail(request.getEmail())
                .orElse(null);
        if (email != null) {
            throw new ResourceNotFoundExceptionRequest("Email not valid");
        }
        var number = userRepository.getUserByNumber(request.getNumber())
                .orElse(null);
        if (number != null) {
            throw new ResourceNotFoundExceptionRequest("Number not valid");
        }
        var entity = mapper.map(request, User.class);
        entity.setPoints(0L);
        var passwordEncrypt = encoder.encode(request.getPassword());
        entity.setPassword(passwordEncrypt);

        try {
            userRepository.save(entity);
            var response = mapper.map(entity, UserResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating user");
        }
    }

    @Override
    public UserResponse update(UserRequest request, Long id) {
        var entity = userRepository.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("User not found by id"));
        if (entity.getUsername() != request.getUsername()) {
            var username = userRepository.getUserByUsername(request.getUsername())
                    .orElse(null);
            if (username != null) {
                throw new ResourceNotFoundExceptionRequest("Username not valid");
            }
        }

        if (entity.getNumber() != request.getNumber()) {
            var number = userRepository.getUserByNumber(request.getNumber())
                    .orElse(null);
            if (number != null) {
                throw new ResourceNotFoundExceptionRequest("Number not valid");
            }
        }

        if (entity.getEmail() != request.getEmail()) {
            var email = userRepository.getUserByEmail(request.getEmail())
                    .orElse(null);
            if (email != null) {
                throw new ResourceNotFoundExceptionRequest("Email not valid");
            }
        }

        entity.setBirthday(request.getBirthday());
        entity.setCreatedDate(request.getCreatedDate());
        entity.setEmail(request.getEmail());
        entity.setGameFavorite1(request.getGameFavorite1());
        entity.setGameFavorite2(request.getGameFavorite2());
        entity.setGameFavorite3(request.getGameFavorite3());
        entity.setLastName(request.getLastName());
        entity.setName(request.getName());
        entity.setNumber(request.getNumber());
        entity.setUsername(request.getUsername());
        var passwordEncrypt = encoder.encode(request.getPassword());
        entity.setPassword(passwordEncrypt);

        try {
            userRepository.save(entity);
            var response = mapper.map(entity, UserResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating user");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting user");
        }
    }

    @Override
    public UserResponse updateWallet(Long points, Long id) {
        var entity = userRepository.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("User not found by id"));

        entity.setPoints(entity.getPoints() - points);
        try {
            userRepository.save(entity);
            var response = mapper.map(entity, UserResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating wallet user");
        }
    }

}
