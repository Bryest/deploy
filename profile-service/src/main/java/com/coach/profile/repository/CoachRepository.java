package com.coach.profile.repository;

import java.util.List;
import java.util.Optional;

import com.coach.profile.entity.Coach;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {
    Optional<Coach> getCoachByEmail(String email);

    Optional<Coach> getCoachByUsername(String username);

    Optional<Coach> getCoachByNumber(String number);

    Optional<Coach> getCoachByNameCoach(String nameCoach);

    Optional<Coach> getCoachById(Long id);

    List<Coach> findAllByGameId(Long id);
}
