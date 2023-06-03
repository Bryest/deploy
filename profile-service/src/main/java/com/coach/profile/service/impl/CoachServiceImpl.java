package com.coach.profile.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.coach.exception.ResourceNotFoundExceptionRequest;
import com.coach.profile.dto.AuthenticateRequest;
import com.coach.profile.dto.CoachRequest;
import com.coach.profile.dto.CoachResponse;
import com.coach.profile.dto.CoachResponseSimple;
import com.coach.profile.entity.Coach;
import com.coach.profile.repository.CoachRepository;
import com.coach.profile.service.CoachService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CoachServiceImpl implements CoachService {

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<CoachResponse> getAll() {
        var entities = coachRepository.findAll();
        var response = entities.stream().map(entity -> mapper.map(entity, CoachResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public List<CoachResponseSimple> getAllByGameId(Long id) {
        var entities = coachRepository.findAllByGameId(id);
        var response = entities.stream().map(entity -> mapper.map(entity, CoachResponseSimple.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public CoachResponse getById(Long id) {
        var entity = coachRepository.getCoachById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("User not found by id"));

        var response = mapper.map(entity, CoachResponse.class);
        return response;
    }

    @Override
    public CoachResponse getByUsernameAndPassword(AuthenticateRequest request) {
        var entity = coachRepository.getCoachByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("User not found by id"));
        var valid = encoder.matches(request.getPassword(), entity.getPassword());
        if (valid) {
            var response = mapper.map(entity, CoachResponse.class);
            return response;
        }
        throw new ResourceNotFoundExceptionRequest("Credentials invalids");
    }

    @Override
    public CoachResponse create(CoachRequest request) {
        var username = coachRepository.getCoachByUsername(request.getUsername())
                .orElse(null);
        if (username != null) {
            throw new ResourceNotFoundExceptionRequest("Username not valid");
        }
        var email = coachRepository.getCoachByEmail(request.getEmail())
                .orElse(null);
        if (email != null) {
            throw new ResourceNotFoundExceptionRequest("Email not valid");
        }
        var number = coachRepository.getCoachByNumber(request.getNumber())
                .orElse(null);
        if (number != null) {
            throw new ResourceNotFoundExceptionRequest("Number not valid");
        }
        var nameCoach = coachRepository.getCoachByNameCoach(request.getNameCoach())
                .orElse(null);
        if (nameCoach != null) {
            throw new ResourceNotFoundExceptionRequest("Name coach not valid");
        }

        var entity = new Coach();
        entity.setBirthday(request.getBirthday());
        entity.setCreatedDate(request.getCreatedDate());
        entity.setDescription(request.getDescription());
        entity.setEmail(request.getEmail());
        entity.setGameId(request.getGameId());
        entity.setLastName(request.getLastName());
        entity.setName(request.getName());
        entity.setNameCoach(request.getNameCoach());
        entity.setNumber(request.getNumber());
        entity.setPoints(0L);
        var passwordEncrypt = encoder.encode(request.getPassword());
        entity.setPassword(passwordEncrypt);
        entity.setUsername(request.getUsername());

        try {
            coachRepository.save(entity);
            var response = mapper.map(entity, CoachResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating coach");
        }
    }

    @Override
    public CoachResponse update(CoachRequest request, Long id) {
        var entity = coachRepository.getCoachById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("User not found by id"));

        if (entity.getUsername() != request.getUsername()) {
            var username = coachRepository.getCoachByUsername(request.getUsername())
                    .orElse(null);

            if (username != null) {
                throw new ResourceNotFoundExceptionRequest("Username not valid");
            }
        }

        if (entity.getEmail() != request.getEmail()) {
            var email = coachRepository.getCoachByEmail(request.getEmail())
                    .orElse(null);
            if (email != null) {
                throw new ResourceNotFoundExceptionRequest("Email not valid");
            }
        }

        if (entity.getNumber() != request.getNumber()) {
            var number = coachRepository.getCoachByNumber(request.getNumber())
                    .orElse(null);
            if (number != null) {
                throw new ResourceNotFoundExceptionRequest("Number not valid");
            }
        }

        if (entity.getNameCoach() != request.getNameCoach()) {
            var nameCoach = coachRepository.getCoachByNameCoach(request.getNameCoach())
                    .orElse(null);
            if (nameCoach != null) {
                throw new ResourceNotFoundExceptionRequest("Name coach not valid");
            }
        }

        entity.setBirthday(request.getBirthday());
        entity.setCreatedDate(request.getCreatedDate());
        entity.setDescription(request.getDescription());
        entity.setEmail(request.getEmail());
        entity.setGameId(request.getGameId());
        entity.setLastName(request.getLastName());
        entity.setName(request.getName());
        entity.setNameCoach(request.getNameCoach());
        entity.setNumber(request.getNumber());
        entity.setUsername(request.getUsername());

        var passwordEncrypt = encoder.encode(request.getPassword());
        entity.setPassword(passwordEncrypt);

        try {
            coachRepository.save(entity);
            var response = mapper.map(entity, CoachResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating coach");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            coachRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting coach");
        }
    }

    @Override
    public CoachResponse updateWallet(Long id, Long point) {
        var entity = coachRepository.getCoachById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("User not found by id"));

        entity.setPoints(entity.getPoints() + point);

        try {
            coachRepository.save(entity);
            var response = mapper.map(entity, CoachResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating wallet of coach");
        }
    }

}
