package com.user.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.user.exception.ResourceNotFoundExceptionRequest;
import com.user.security.dto.UserRequest;
import com.user.security.dto.UserResponse;
import com.user.security.entity.User;
import com.user.security.repository.UserRepository;
import com.user.security.service.impl.UserServiceImpl;

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
public class UserServiceImplMockTest {

    @Spy
    private UserRepository userRepository;

    @Spy
    private BCryptPasswordEncoder encoder;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    User user;

    User user2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setBirthday(new Date());
        user.setCreatedDate(new Date());
        user.setEmail("email");
        user.setGameFavorite1(1L);
        user.setGameFavorite2(2L);
        user.setGameFavorite3(3L);
        user.setId(1L);
        user.setLastName("lastName");
        user.setName("name");
        user.setNumber("number");
        user.setPassword("password");
        user.setPoints(1L);
        user.setUsername("username");

        user2 = new User();
        user2.setBirthday(new Date());
        user2.setCreatedDate(new Date());
        user2.setEmail("email1");
        user2.setGameFavorite1(1L);
        user2.setGameFavorite2(2L);
        user2.setGameFavorite3(3L);
        user2.setId(2L);
        user2.setLastName("lastName1");
        user2.setName("name1");
        user2.setNumber("number1");
        user2.setPassword("password1");
        user2.setPoints(1L);
        user2.setUsername("username1");
    }

    @Test
    @DisplayName("When Find All Then Return List User")
    public void WhenFindAllThenReturnListUser() {
        // Arrange
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);
        // Act
        List<UserResponse> response = userServiceImpl.getAll();

        // Assert
        assertEquals(2L, response.size());
    }

    @Test
    @DisplayName("When Find By Id Then Return User")
    public void WhenFindByIdThenReturnUser() {
        // Arrange
        when(userRepository.getUserById(1L)).thenReturn(Optional.of(user));
        // Act
        UserResponse response = userServiceImpl.getById(1L);

        // Assert
        assertEquals(1L, response.getId());
        assertEquals("username", response.getUsername());
        assertEquals("email", response.getEmail());
        assertEquals("number", response.getNumber());
        assertEquals("password", response.getPassword());
    }

    @Test
    @DisplayName("When Find By Id But Not Exist Id")
    public void WhenFindByIdButNotExistId() {
        // Arrange
        when(userRepository.getUserById(3L)).thenReturn(Optional.empty());
        // Act
        String message = "User not found by id";
        Throwable exception = catchThrowable(() -> {
            userServiceImpl.getById(3L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create An User Then Return User")
    public void WhenCreateAnUserThenReturnUser() {
        // Arrange
        UserRequest request = new UserRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setEmail("email2");
        request.setGameFavorite1(1L);
        request.setGameFavorite2(2L);
        request.setGameFavorite3(3L);
        request.setLastName("lastName");
        request.setName("name");
        request.setNumber("number2");
        request.setPassword("password");
        request.setUsername("username2");

        when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(user));
        when(userRepository.getUserByNumber("number")).thenReturn(Optional.of(user));
        when(userRepository.getUserByUsername("username")).thenReturn(Optional.of(user));
        when(userRepository.getUserById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(Mockito.any())).thenReturn(user2);
        // Act
        UserResponse response = userServiceImpl.create(request);
        Boolean passwordValid = encoder.matches("password", response.getPassword());

        // Assert
        assertEquals(true, passwordValid);
        assertEquals("email2", response.getEmail());
        assertEquals("username2", response.getUsername());
    }

    @Test
    @DisplayName("When Create An User But Email Is Using")
    public void WhenCreateAnUserButEmailIsUsing() {
        // Arrange
        UserRequest request = new UserRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setEmail("email");
        request.setGameFavorite1(1L);
        request.setGameFavorite2(2L);
        request.setGameFavorite3(3L);
        request.setLastName("lastName");
        request.setName("name");
        request.setNumber("number2");
        request.setPassword("password");
        request.setUsername("username2");

        when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(user));
        when(userRepository.getUserByNumber("number")).thenReturn(Optional.of(user));
        when(userRepository.getUserByUsername("username")).thenReturn(Optional.of(user));
        when(userRepository.getUserById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(Mockito.any())).thenReturn(user2);
        // Act
        String message = "Email not valid";
        Throwable exception = catchThrowable(() -> {
            userServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create An User But Usename Is Using")
    public void WhenCreateAnUserButUsernameIsUsing() {
        // Arrange
        UserRequest request = new UserRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setEmail("email2");
        request.setGameFavorite1(1L);
        request.setGameFavorite2(2L);
        request.setGameFavorite3(3L);
        request.setLastName("lastName");
        request.setName("name");
        request.setNumber("number2");
        request.setPassword("password");
        request.setUsername("username");

        when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(user));
        when(userRepository.getUserByNumber("number")).thenReturn(Optional.of(user));
        when(userRepository.getUserByUsername("username")).thenReturn(Optional.of(user));
        when(userRepository.getUserById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(Mockito.any())).thenReturn(user2);
        // Act
        String message = "Username not valid";
        Throwable exception = catchThrowable(() -> {
            userServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create An User But Number Is Using")
    public void WhenCreateAnUserButNumberIsUsing() {
        // Arrange
        UserRequest request = new UserRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setEmail("email2");
        request.setGameFavorite1(1L);
        request.setGameFavorite2(2L);
        request.setGameFavorite3(3L);
        request.setLastName("lastName");
        request.setName("name");
        request.setNumber("number");
        request.setPassword("password");
        request.setUsername("username2");

        when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(user));
        when(userRepository.getUserByNumber("number")).thenReturn(Optional.of(user));
        when(userRepository.getUserByUsername("username")).thenReturn(Optional.of(user));
        when(userRepository.getUserById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(Mockito.any())).thenReturn(user2);
        // Act
        String message = "Number not valid";
        Throwable exception = catchThrowable(() -> {
            userServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update An User Then Return User")
    public void WhenUpdateAnUserThenReturnUser() {
        // Arrange
        UserRequest request = new UserRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setEmail("email2");
        request.setGameFavorite1(1L);
        request.setGameFavorite2(2L);
        request.setGameFavorite3(3L);
        request.setLastName("lastName");
        request.setName("name");
        request.setNumber("number2");
        request.setPassword("password");
        request.setUsername("username2");

        when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(user));
        when(userRepository.getUserByNumber("number")).thenReturn(Optional.of(user));
        when(userRepository.getUserByUsername("username")).thenReturn(Optional.of(user));
        when(userRepository.getUserById(1L)).thenReturn(Optional.of(user));
        when(userRepository.getUserById(2L)).thenReturn(Optional.of(user2));
        when(userRepository.save(Mockito.any())).thenReturn(user2);

        // Act
        UserResponse response = userServiceImpl.update(request, 2L);
        Boolean passwordValid = encoder.matches("password", response.getPassword());

        // Assert
        assertEquals(true, passwordValid);
        assertEquals("email2", response.getEmail());
        assertEquals("username2", response.getUsername());
    }

    @Test
    @DisplayName("When Update An User But Email Is Using")
    public void WhenUpdateAnUserButEmailIsUsing() {
        // Arrange
        UserRequest request = new UserRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setEmail("email");
        request.setGameFavorite1(1L);
        request.setGameFavorite2(2L);
        request.setGameFavorite3(3L);
        request.setLastName("lastName");
        request.setName("name");
        request.setNumber("number2");
        request.setPassword("password");
        request.setUsername("username2");

        when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(user));
        when(userRepository.getUserByNumber("number")).thenReturn(Optional.of(user));
        when(userRepository.getUserByUsername("username")).thenReturn(Optional.of(user));
        when(userRepository.getUserById(1L)).thenReturn(Optional.of(user));
        when(userRepository.getUserById(2L)).thenReturn(Optional.of(user2));
        when(userRepository.save(Mockito.any())).thenReturn(user2);

        // Act
        String message = "Email not valid";
        Throwable exception = catchThrowable(() -> {
            userServiceImpl.update(request, 2L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update An User But Username Is Using")
    public void WhenUpdateAnUserButUsernameIsUsing() {
        // Arrange
        UserRequest request = new UserRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setEmail("email2");
        request.setGameFavorite1(1L);
        request.setGameFavorite2(2L);
        request.setGameFavorite3(3L);
        request.setLastName("lastName");
        request.setName("name");
        request.setNumber("number2");
        request.setPassword("password");
        request.setUsername("username");

        when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(user));
        when(userRepository.getUserByNumber("number")).thenReturn(Optional.of(user));
        when(userRepository.getUserByUsername("username")).thenReturn(Optional.of(user));
        when(userRepository.getUserById(1L)).thenReturn(Optional.of(user));
        when(userRepository.getUserById(2L)).thenReturn(Optional.of(user2));
        when(userRepository.save(Mockito.any())).thenReturn(user2);

        // Act
        String message = "Username not valid";
        Throwable exception = catchThrowable(() -> {
            userServiceImpl.update(request, 2L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update An User But Number Is Using")
    public void WhenUpdateAnUserButNumberIsUsing() {
        // Arrange
        UserRequest request = new UserRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setEmail("email2");
        request.setGameFavorite1(1L);
        request.setGameFavorite2(2L);
        request.setGameFavorite3(3L);
        request.setLastName("lastName");
        request.setName("name");
        request.setNumber("number");
        request.setPassword("password");
        request.setUsername("username2");

        when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(user));
        when(userRepository.getUserByNumber("number")).thenReturn(Optional.of(user));
        when(userRepository.getUserByUsername("username")).thenReturn(Optional.of(user));
        when(userRepository.getUserById(1L)).thenReturn(Optional.of(user));
        when(userRepository.getUserById(2L)).thenReturn(Optional.of(user2));
        when(userRepository.save(Mockito.any())).thenReturn(user2);

        // Act
        String message = "Number not valid";
        Throwable exception = catchThrowable(() -> {
            userServiceImpl.update(request, 2L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update An User But Error Ocurred While Updating")
    public void WhenUpdateAnUserButErrorOcurredWhileUpdating() {
        // Arrange
        UserRequest request = new UserRequest();
        request.setBirthday(new Date());
        request.setCreatedDate(new Date());
        request.setEmail("email2");
        request.setGameFavorite1(1L);
        request.setGameFavorite2(2L);
        request.setGameFavorite3(3L);
        request.setLastName("lastName");
        request.setName("name");
        request.setNumber("number2");
        request.setPassword("password");
        request.setUsername("username2");

        when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(user));
        when(userRepository.getUserByNumber("number")).thenReturn(Optional.of(user));
        when(userRepository.getUserByUsername("username")).thenReturn(Optional.of(user));
        when(userRepository.getUserById(1L)).thenReturn(Optional.of(user));
        when(userRepository.getUserById(2L)).thenReturn(Optional.of(user2));
        when(userRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);

        // Act
        String message = "Error ocurred while updating user";
        Throwable exception = catchThrowable(() -> {
            userServiceImpl.update(request, 2L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delete An User Is Successful")
    public void WhenDeleteAnUserIsSuccessful() {
        // Arrange
        doNothing().when(userRepository).deleteById(1L);

        // Act
        Throwable exception = catchThrowable(() -> {
            userServiceImpl.delete(1L);
        });

        // Assert
        assertThat(exception).isEqualTo(null);

    }

    @Test
    @DisplayName("When Delete An User But Error Ocurred While Deleting")
    public void WhenDeleteAnUserButErrorOcurredWhileDeleting() {
        // Arrange
        doThrow(ResourceNotFoundExceptionRequest.class).when(userRepository).deleteById(1L);

        // Act
        String message = "Error ocurred while deleting user";
        Throwable exception = catchThrowable(() -> {
            userServiceImpl.delete(1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);

    }

}
