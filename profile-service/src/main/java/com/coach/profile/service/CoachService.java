package com.coach.profile.service;

import java.util.List;

import com.coach.profile.dto.AuthenticateRequest;
import com.coach.profile.dto.CoachRequest;
import com.coach.profile.dto.CoachResponse;
import com.coach.profile.dto.CoachResponseSimple;

public interface CoachService {
    List<CoachResponse> getAll();

    List<CoachResponseSimple> getAllByGameId(Long id);

    CoachResponse getById(Long id);

    CoachResponse getByUsernameAndPassword(AuthenticateRequest request);

    CoachResponse create(CoachRequest request);

    CoachResponse update(CoachRequest request, Long id);

    CoachResponse updateWallet(Long id, Long point);

    void delete(Long id);
}
