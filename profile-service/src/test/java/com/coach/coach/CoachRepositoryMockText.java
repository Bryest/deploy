package com.coach.coach;

import java.util.Date;
import java.util.List;

import com.coach.profile.entity.Coach;
import com.coach.profile.repository.CoachRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CoachRepositoryMockText {

    @Autowired
    private CoachRepository coachRepository;

    @BeforeEach
    public void setUp() {
        Coach coach = new Coach();
        coach.setBirthday(new Date());
        coach.setCreatedDate(new Date());
        coach.setDescription("description 1");
        coach.setEmail("email");
        coach.setGameId(1L);
        coach.setLastName("lastName");
        coach.setName("name");
        coach.setNameCoach("nameCoach");
        coach.setNumber("number");
        coach.setPassword("password");
        coach.setPoints(10L);
        coach.setUsername("username");
        coachRepository.save(coach);
    }

    @Test
    @DisplayName("When Find All Then Return List Coach")
    public void whenFindAllThenReturnListCoach() {
        Coach coach = new Coach();
        coach.setBirthday(new Date());
        coach.setCreatedDate(new Date());
        coach.setDescription("description 2");
        coach.setEmail("email1");
        coach.setGameId(2L);
        coach.setLastName("lastName1");
        coach.setName("name1");
        coach.setNameCoach("nameCoach1");
        coach.setNumber("number1");
        coach.setPassword("password2");
        coach.setPoints(10L);
        coach.setUsername("username1");
        coachRepository.save(coach);

        List<Coach> coachs = coachRepository.findAll();
        Assertions.assertEquals(2L, coachs.size());
    }

}
