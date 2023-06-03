package com.game.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.game.exception.ResourceNotFoundExceptionRequest;
import com.game.games.dto.GameRequest;
import com.game.games.dto.GameResponse;
import com.game.games.entity.Game;
import com.game.games.repository.GameRepository;
import com.game.games.service.impl.GameServiceImpl;


import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class GameServiceImplMockTest {

    @Spy
    private GameRepository gameRepository;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private GameServiceImpl GameServiceImpl;

    Game game;
    Game game2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        game = new Game();
        game.setAge("2000");
        game.setBackground("background");
        game.setDescription("description");
        game.setId(1L);
        game.setName("name");
        game.setSubtitle("subtitle");
        game.setImage("image");

    }
    @Test
    @DisplayName("When Find By Id Then Return Game")
    public void WhenFindByIdThenReturnGame() {
        // Arrange
        when(gameRepository.getGameById(1L)).thenReturn(Optional.of(game));

        // Act
        GameResponse response = GameServiceImpl.getById(1L);

        // Assert
        assertEquals(1L, game.getId());
        assertEquals("2000", response.getAge());
        assertEquals("background", response.getBackground());
        assertEquals("description", response.getDescription());
        assertEquals("name", response.getName());
        assertEquals("subtitle", response.getSubtitle());
        assertEquals("image", response.getImage());
    }

    @Test
    @DisplayName("When Find By Id But Not Exist Id")
    public void WhenFindByIdButNotExistId() {
        // Arrange
        when(gameRepository.getGameById(2L)).thenReturn(Optional.empty());

        // Act
        String message = "Game not found";
        Throwable exception = catchThrowable(() -> {
            GameServiceImpl.getById(2L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class)
                .hasMessageContaining(message);
    }

    @Test
    @DisplayName("When Create A Game Then Return Game")
    public void WhenCreateAGameThenReturnGame() {
        // Arrange
        GameRequest request = new GameRequest();

        request.setAge("age");
        request.setBackground("background");
        request.setDescription("description");
        request.setImage("image");
        request.setName("name");
        request.setSubtitle("subtitle");

        
        when(gameRepository.save(Mockito.any())).thenReturn(game2);
        // Act
        GameResponse response = GameServiceImpl.create(request);
        

        // Assert
        assertEquals(1L, game.getId());
        assertEquals("age", response.getAge());
        assertEquals("background", response.getBackground());
        assertEquals("description", response.getDescription());
        assertEquals("image", response.getImage());
        assertEquals("name", response.getName());
        assertEquals("subtitle", response.getSubtitle());
    }

    @Test
    @DisplayName("When Create A Game But Error Ocurred")
    public void WhenCreateAGameButErrorOcurred() {
        // Arrange
        GameRequest request = new GameRequest();

        request.setAge("age");
        request.setBackground("background");
        request.setDescription("description");
        request.setImage("image");
        request.setName("name");
        request.setSubtitle("subtitle");

        
        when(gameRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        // Act
        String message = "Error ocurred while creating game";
        Throwable exception = catchThrowable(() -> {
            GameServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update A Game Return A Game")
    public void WhenUpdateAGameReturnAGame() {
        // Arrange
        GameRequest request = new GameRequest();

        request.setAge("age");
        request.setBackground("background");
        request.setDescription("description");
        request.setImage("image");
        request.setName("name");
        request.setSubtitle("subtitle");

        
        when(gameRepository.save(Mockito.any())).thenReturn(game);
        when(gameRepository.getGameById(1L)).thenReturn(Optional.of(game));
        // Act
       
        GameResponse response = GameServiceImpl.update(request, 1L);

        // Assert

        assertEquals(1L, response.getId());
        assertEquals("age", response.getAge());
        assertEquals("background", response.getBackground());
        assertEquals("description", response.getDescription());
        assertEquals("image", response.getImage());
        assertEquals("name", response.getName());
        assertEquals("subtitle", response.getSubtitle());

    }

    @Test
    @DisplayName("When Update A Game But Game Not Found")
    public void WhenUpdateAGameButGameNotFound() {
        // Arrange
        GameRequest request = new GameRequest();

        request.setAge("age");
        request.setBackground("background");
        request.setDescription("description");
        request.setImage("image");
        request.setName("name");
        request.setSubtitle("subtitle");

        
        when(gameRepository.save(Mockito.any())).thenReturn(game);
        
        // Arrange
        when(gameRepository.getGameById(2L)).thenReturn(Optional.empty());

        // Act
        String message = "Game not found";
        Throwable exception = catchThrowable(() -> {
            GameServiceImpl.update(request, 1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class)
                .hasMessageContaining(message);

    }

    @Test
    @DisplayName("When Update A Game But Error Ocurred")
    public void WhenUpdateAGameButErrorOcurred() {
        // Arrange
        GameRequest request = new GameRequest();

        request.setAge("age");
        request.setBackground("background");
        request.setDescription("description");
        request.setImage("image");
        request.setName("name");
        request.setSubtitle("subtitle");

        
        when(gameRepository.getGameById(1L)).thenReturn(Optional.of(game));

        when(gameRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        // Act
        String message = "Error ocurred while updating game";
        Throwable exception = catchThrowable(() -> {
            GameServiceImpl.update(request, 1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);

    }

    @Test
    @DisplayName("When Delete A Game By Id Is Successful")
    public void WhenDeleteAGameByIdIsSuccessful() {
        // Arrange
        doNothing().when(gameRepository).deleteById(1L);
        // Act
        Throwable exception = catchThrowable(() -> {
            GameServiceImpl.delete(1L);
        });
        // Assert
        assertThat(exception).isEqualTo(null);
    }

    @Test
    @DisplayName("When Delete A Game But Error Ocurred While Deleting")
    public void WhenDeleteAGameButErrorOcurredWhileDeleting() {
        // Arrange
        doThrow(ResourceNotFoundExceptionRequest.class).when(gameRepository).deleteById(1L);
        // Act
        String message = "Error ocurred while deleting game";
        Throwable exception = catchThrowable(() -> {
            GameServiceImpl.delete(1L);
        });
        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }
    
}
