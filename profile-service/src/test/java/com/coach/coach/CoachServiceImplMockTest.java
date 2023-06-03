package com.coach.coach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import com.coach.exception.ResourceNotFoundExceptionRequest;
import com.coach.profile.dto.CoachRequest;
import com.coach.profile.dto.CoachResponse;
import com.coach.profile.entity.Coach;
import com.coach.profile.repository.CoachRepository;
import com.coach.profile.service.impl.CoachServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class CoachServiceImplMockTest {

    @Spy
    private CoachRepository coachRepository;

    @Spy
    private ModelMapper mapper;

    @Spy
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private CoachServiceImpl coachServiceImpl;

    Coach coach;

    Coach coach2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        coach = new Coach();
        coach.setId(1L);
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

        coach2 = new Coach();
        coach2.setId(2L);
        coach2.setBirthday(new Date());
        coach2.setCreatedDate(new Date());
        coach2.setDescription("description");
        coach2.setEmail("email1");
        coach2.setGameId(1L);
        coach2.setLastName("lastName1");
        coach2.setName("name1");
        coach2.setNameCoach("nameCoach1");
        coach2.setNumber("number1");
        coach2.setPassword("password1");
        coach2.setUsername("username1");
    }

    @Test
    @DisplayName("When Find By Id Then Return Coach")
    public void WhenFindByIdThenReturnCoach() {
        // Arrange
        when(coachRepository.getCoachById(1L)).thenReturn(Optional.of(coach));

        // Act
        CoachResponse response = coachServiceImpl.getById(1L);

        // Assert
        assertEquals(1L, coach.getId());
        assertEquals("username", response.getUsername());
        assertEquals("email", response.getEmail());
        assertEquals("number", response.getNumber());
        assertEquals("password", response.getPassword());
    }

    @Test
    @DisplayName("When Find By Id But Not Exist Id")
    public void WhenFindByIdButNotExistId() {
        // Arrange
        when(coachRepository.getCoachById(2L)).thenReturn(Optional.empty());

        // Act
        String message = "User not found by id";
        Throwable exception = catchThrowable(() -> {
            coachServiceImpl.getById(2L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class)
                .hasMessageContaining(message);
    }

    @Test
    @DisplayName("When Create An Coach Then Return Coach")
    public void WhenCreateAnCoachThenReturnCoach() {
        // Arrange
        CoachRequest request = new CoachRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setDescription("description 1");
        request.setEmail("email1");
        request.setGameId(1L);
        request.setLastName("lastName1");
        request.setName("name1");
        request.setNameCoach("nameCoach1");
        request.setNumber("number1");
        request.setPassword("password1");
        request.setUsername("username1");

        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByEmail("email")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNameCoach("nameCoach")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNumber("number")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.save(Mockito.any())).thenReturn(coach2);
        // Act
        CoachResponse response = coachServiceImpl.create(request);
        Boolean passwordValid = encoder.matches("password1", response.getPassword());

        // Assert
        assertEquals(true, passwordValid);
        assertEquals(1L, coach.getId());
        assertEquals("username1", response.getUsername());
        assertEquals("email1", response.getEmail());
        assertEquals("number1", response.getNumber());
    }

    @Test
    @DisplayName("When Create An Coach But Error Ocurred While Creating")
    public void WhenCreateAnCoachButErrorOcurredWhileCreating() {
        // Arrange
        CoachRequest request = new CoachRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setDescription("description 1");
        request.setEmail("email1");
        request.setGameId(1L);
        request.setLastName("lastName1");
        request.setName("name1");
        request.setNameCoach("nameCoach1");
        request.setNumber("number1");
        request.setPassword("password1");
        request.setUsername("username1");

        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByEmail("email")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNameCoach("nameCoach")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNumber("number")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        // Act
        String message = "Error ocurred while creating coach";
        Throwable exception = catchThrowable(() -> {
            coachServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create An Coach But The Username Is Using")
    public void WhenCreateAnCoachButTheUsernameIsUsing() {
        // Arrange
        CoachRequest request = new CoachRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setDescription("description 1");
        request.setEmail("email1");
        request.setGameId(1L);
        request.setLastName("lastName1");
        request.setName("name1");
        request.setNameCoach("nameCoach1");
        request.setNumber("number1");
        request.setPassword("password1");
        request.setUsername("username");

        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByEmail("email")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNameCoach("nameCoach")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNumber("number")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        // Act
        String message = "Username not valid";
        Throwable exception = catchThrowable(() -> {
            coachServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create An Coach But Email Is Using")
    public void WhenCreateAnCoachButEmailIsUsing() {
        // Arrange
        CoachRequest request = new CoachRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setDescription("description 1");
        request.setEmail("email");
        request.setGameId(1L);
        request.setLastName("lastName1");
        request.setName("name1");
        request.setNameCoach("nameCoach1");
        request.setNumber("number1");
        request.setPassword("password1");
        request.setUsername("username1");

        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByEmail("email")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNameCoach("nameCoach")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNumber("number")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        // Act
        String message = "Email not valid";
        Throwable exception = catchThrowable(() -> {
            coachServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create An Coach But The Number Is Using")
    public void WhenCreateAnCoachButTheNumberIsUsing() {
        // Arrange
        CoachRequest request = new CoachRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setDescription("description 1");
        request.setEmail("email1");
        request.setGameId(1L);
        request.setLastName("lastName1");
        request.setName("name1");
        request.setNameCoach("nameCoach1");
        request.setNumber("number");
        request.setPassword("password1");
        request.setUsername("username1");

        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByEmail("email")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNameCoach("nameCoach")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNumber("number")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        // Act
        String message = "Number not valid";
        Throwable exception = catchThrowable(() -> {
            coachServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create An Coach But The Name Coach Is Using")
    public void WhenCreateAnCoachButTheNameCoachIsUsing() {
        // Arrange
        CoachRequest request = new CoachRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setDescription("description 1");
        request.setEmail("email1");
        request.setGameId(1L);
        request.setLastName("lastName1");
        request.setName("name1");
        request.setNameCoach("nameCoach");
        request.setNumber("number1");
        request.setPassword("password1");
        request.setUsername("username1");

        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByEmail("email")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNameCoach("nameCoach")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNumber("number")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        // Act
        String message = "Name coach not valid";
        Throwable exception = catchThrowable(() -> {
            coachServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update An Coach Then Return Coach")
    public void WhenUpdateAnCoachThenReturnCoach() {
        // Arrange
        CoachRequest request = new CoachRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setDescription("description");
        request.setEmail("email1");
        request.setGameId(1L);
        request.setLastName("lastName1");
        request.setName("name1");
        request.setNameCoach("nameCoach");
        request.setNumber("number1");
        request.setPassword("password1");
        request.setUsername("username1");

        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByEmail("email")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNameCoach("nameCoach")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNumber("number")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.save(Mockito.any())).thenReturn(coach);
        when(coachRepository.getCoachById(1L)).thenReturn(Optional.of(coach));
        // Act
        CoachResponse response = coachServiceImpl.update(request, 1L);

        // Assert
        assertEquals(1L, response.getId());
        assertEquals("email1", response.getEmail());
        assertEquals("name1", response.getName());
        assertEquals("number1", response.getNumber());
        assertEquals("username1", response.getUsername());
    }

    @Test
    @DisplayName("When Update An Coach But The Username Is Usign")
    public void WhenUpdateAnCoachButTheUsernameIsUsign() {
        // Arrange
        CoachRequest request = new CoachRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setDescription("description");
        request.setEmail("email1");
        request.setGameId(1L);
        request.setLastName("lastName1");
        request.setName("name1");
        request.setNameCoach("nameCoach");
        request.setNumber("number1");
        request.setPassword("password1");
        request.setUsername("username");

        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByEmail("email")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNameCoach("nameCoach")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNumber("number")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.save(Mockito.any())).thenReturn(coach);
        when(coachRepository.getCoachById(1L)).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachById(2L)).thenReturn(Optional.of(coach2));
        // Act
        String message = "Username not valid";
        Throwable exception = catchThrowable(() -> {
            coachServiceImpl.update(request, 2L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update An Coach But The Number Is Usign")
    public void WhenUpdateAnCoachButTheNumberIsUsign() {
        // Arrange
        CoachRequest request = new CoachRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setDescription("description");
        request.setEmail("email1");
        request.setGameId(1L);
        request.setLastName("lastName1");
        request.setName("name1");
        request.setNameCoach("nameCoach1");
        request.setNumber("number");
        request.setPassword("password1");
        request.setUsername("username1");

        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByEmail("email")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNameCoach("nameCoach")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNumber("number")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.save(Mockito.any())).thenReturn(coach);
        when(coachRepository.getCoachById(1L)).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachById(2L)).thenReturn(Optional.of(coach2));
        // Act
        String message = "Number not valid";
        Throwable exception = catchThrowable(() -> {
            coachServiceImpl.update(request, 2L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update An Coach But The Email Is Usign")
    public void WhenUpdateAnCoachButTheEmailIsUsign() {
        // Arrange
        CoachRequest request = new CoachRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setDescription("description");
        request.setEmail("email");
        request.setGameId(1L);
        request.setLastName("lastName1");
        request.setName("name1");
        request.setNameCoach("nameCoach1");
        request.setNumber("number1");
        request.setPassword("password1");
        request.setUsername("username1");

        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByEmail("email")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNameCoach("nameCoach")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNumber("number")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.save(Mockito.any())).thenReturn(coach);
        when(coachRepository.getCoachById(1L)).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachById(2L)).thenReturn(Optional.of(coach2));
        // Act
        String message = "Email not valid";
        Throwable exception = catchThrowable(() -> {
            coachServiceImpl.update(request, 2L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update An Coach But The Name Coach Is Usign")
    public void WhenUpdateAnCoachButTheNameCoachIsUsign() {
        // Arrange
        CoachRequest request = new CoachRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setDescription("description");
        request.setEmail("email1");
        request.setGameId(1L);
        request.setLastName("lastName1");
        request.setName("name1");
        request.setNameCoach("nameCoach");
        request.setNumber("number1");
        request.setPassword("password1");
        request.setUsername("username1");

        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByEmail("email")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNameCoach("nameCoach")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNumber("number")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.save(Mockito.any())).thenReturn(coach);
        when(coachRepository.getCoachById(1L)).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachById(2L)).thenReturn(Optional.of(coach2));
        // Act
        String message = "Name coach not valid";
        Throwable exception = catchThrowable(() -> {
            coachServiceImpl.update(request, 2L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update An Coach But Error Ocurred While Updating")
    public void WhenUpdateAnCoachButErrorOcurredWhileUpdating() {
        // Arrange
        CoachRequest request = new CoachRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setDescription("description");
        request.setEmail("email1");
        request.setGameId(1L);
        request.setLastName("lastName1");
        request.setName("name1");
        request.setNameCoach("nameCoach1");
        request.setNumber("number1");
        request.setPassword("password1");
        request.setUsername("username1");

        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByEmail("email")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNameCoach("nameCoach")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByNumber("number")).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachByUsername("username")).thenReturn(Optional.of(coach));
        when(coachRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        when(coachRepository.getCoachById(1L)).thenReturn(Optional.of(coach));
        when(coachRepository.getCoachById(2L)).thenReturn(Optional.of(coach2));
        // Act
        String message = "Error ocurred while updating coach";
        Throwable exception = catchThrowable(() -> {
            coachServiceImpl.update(request, 2L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delete An Coach By Id Is Successful")
    public void WhenDeleteAnCoachByIdIsSuccessful() {
        // Arrange
        doNothing().when(coachRepository).deleteById(1L);
        // Act
        Throwable exception = catchThrowable(() -> {
            coachServiceImpl.delete(1L);
        });
        // Assert
        assertThat(exception).isEqualTo(null);
    }

    @Test
    @DisplayName("When Delete An Coach But Error Ocurred While Deleting")
    public void WhenDeleteAnCoachButErrorOcurredWhileDeleting() {
        // Arrange
        doThrow(ResourceNotFoundExceptionRequest.class).when(coachRepository).deleteById(1L);
        // Act
        String message = "Error ocurred while deleting coach";
        Throwable exception = catchThrowable(() -> {
            coachServiceImpl.delete(1L);
        });
        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }
}
