package com.game_coin.GameCoinOffer;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.game_coin.exception.ResourceNotFoundExceptionRequest;
import com.game_coin.payments.dto.GameCoinOfferRequest;
import com.game_coin.payments.dto.GameCoinOfferResponse;
import com.game_coin.payments.entity.GameCoinOffer;
import com.game_coin.payments.repository.GameCoinOfferRepository;
import com.game_coin.payments.service.impl.GameCoinServiceImpl;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TestGameCoinServiceImpl {
    @Spy
    private GameCoinOfferRepository gameCoinOfferRepository;

    @InjectMocks
    private GameCoinServiceImpl gameCoinServiceImpl;

    GameCoinOffer gamecoinoffer;
    GameCoinOffer gamecoinoffer1;
    
    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);

        gamecoinoffer = new GameCoinOffer();
        gamecoinoffer.setAvaible(true);
        gamecoinoffer.setCreatedAt(new Date());
        gamecoinoffer.setDescription("Promocion 50% DCTO");
        gamecoinoffer.setId(1L);
        gamecoinoffer.setName("DogeCoin");
        gamecoinoffer.setImage("Url");
        gamecoinoffer.setPoint(50L);
        gamecoinoffer.setPrice(30.0);

        gamecoinoffer1 = new GameCoinOffer();
        gamecoinoffer1.setAvaible(true);
        gamecoinoffer1.setCreatedAt(new Date());
        gamecoinoffer1.setDescription("Promocion 70% DCTO");
        gamecoinoffer1.setId(1L);
        gamecoinoffer1.setName("BitCoin");
        gamecoinoffer1.setImage("Url1");
        gamecoinoffer1.setPoint(50L);
        gamecoinoffer1.setPrice(30.0);
    }

    @Test
    @DisplayName("Get All Return List Game Coin Offer")
    void GetAllReturnListGameCoinOffer(){
        //Arrange
        List<GameCoinOffer> list = new ArrayList<GameCoinOffer>();
        list.add(gamecoinoffer);
        when(gameCoinOfferRepository.findAll()).thenReturn(list);

        //Act
        List<GameCoinOfferResponse> response = gameCoinServiceImpl.getAll();

        //Assert
        assertEquals(1L, response.size());
    }

    @Test
    @DisplayName("Get By Id Return Game Coin Offer")
    void GetByIdReturnGameCoinOffer(){
        // Arrange
        when(gameCoinOfferRepository.getGameCoinOfferById(1L)).thenReturn(Optional.of(gamecoinoffer));
        // Act
        GameCoinOfferResponse response = gameCoinServiceImpl.getById(1L);

        // Assert
        assertEquals(1L, response.getId());
        assertEquals("Promocion 50% DCTO", response.getDescription());
        assertEquals("Url", response.getImage());
        assertEquals("DogeCoin", response.getName());
    }

    @Test
    @DisplayName("When Find By Id But Not Exist Id")
    public void WhenFindByIdButNotExistId() {
        // Arrange
        when(gameCoinOfferRepository.getGameCoinOfferById(1L)).thenReturn(Optional.empty());
        // Act
        String message = "Game coin offer not found";
        Throwable exception = catchThrowable(() -> {
            gameCoinServiceImpl.getById(3L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("Create Game Coin Offer")
    void CreateGameCoinOffer(){
        //Arrange
        GameCoinOfferRequest request = new GameCoinOfferRequest();
        request.setAvaible(true);
        request.setCreatedAt(new Date());
        request.setDescription("Promocion 70% DCTO");
        request.setName("BitCoin");
        request.setImage("Url1");
        request.setPoint(50L);
        request.setPrice(30.0);

        when(gameCoinOfferRepository.save(Mockito.any())).thenReturn(gamecoinoffer1);
        when(gameCoinOfferRepository.getGameCoinOfferById(1L)).thenReturn(Optional.of(gamecoinoffer));

        //Act
        GameCoinOfferResponse response = gameCoinServiceImpl.create(request);

        //Assert
        assertEquals("Promocion 70% DCTO", response.getDescription());
        assertEquals("BitCoin", response.getName());
        assertEquals("Url1", response.getImage());
    }

    @Test
    @DisplayName("When Create A Guide But Error Ocurred While Creating")
    public void WhenCreateButErrorOcurredWhileCreating() {
        //Arrange
        GameCoinOfferRequest request = new GameCoinOfferRequest();
        request.setAvaible(true);
        request.setCreatedAt(new Date());
        request.setDescription("Promocion 70% DCTO");
        request.setName("BitCoin");
        request.setImage("Url1");
        request.setPoint(50L);
        request.setPrice(30.0);

        when(gameCoinOfferRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        when(gameCoinOfferRepository.getGameCoinOfferById(1L)).thenReturn(Optional.of(gamecoinoffer));

        //Act
        final GameCoinOfferRequest finalRequest = request;
        String message = "Error ocurred while creating game coin offer";
        Throwable exception = catchThrowable(() -> {
            gameCoinServiceImpl.create(finalRequest);
        });

        //Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("Update Game Coin Offer")
    void UpdateGameCoinOffer(){
        //Arrange
        GameCoinOfferRequest request = new GameCoinOfferRequest();
        request.setAvaible(true);
        request.setCreatedAt(new Date());
        request.setDescription("Promocion 100% DCTO");
        request.setName("TeslaCoin");
        request.setImage("Url2");
        request.setPoint(50L);
        request.setPrice(30.0);

        when(gameCoinOfferRepository.save(Mockito.any())).thenReturn(gamecoinoffer1);
        when(gameCoinOfferRepository.getGameCoinOfferById(1L)).thenReturn(Optional.of(gamecoinoffer1));

        //Act
        GameCoinOfferResponse response = gameCoinServiceImpl.update(request, 1L);

        //Assert
        assertEquals(1L, response.getId());
        assertEquals("Promocion 100% DCTO", response.getDescription());
        assertEquals("TeslaCoin", response.getName());
        assertEquals("Url2", response.getImage());
    }

    @Test
    @DisplayName("Update Game Coin Offer")
    void WhenUpdateButGameCoinNotFound(){
        //Arrange
        GameCoinOfferRequest request = new GameCoinOfferRequest();
        request.setAvaible(true);
        request.setCreatedAt(new Date());
        request.setDescription("Promocion 100% DCTO");
        request.setName("TeslaCoin");
        request.setImage("Url2");
        request.setPoint(50L);
        request.setPrice(30.0);

        when(gameCoinOfferRepository.save(Mockito.any())).thenReturn(gamecoinoffer1);
        when(gameCoinOfferRepository.getGameCoinOfferById(1L)).thenReturn(Optional.empty());

        //Act
        final GameCoinOfferRequest finalRequest = request;
        String message = "Game coin offer not found";
        Throwable exception = catchThrowable(() -> {
            gameCoinServiceImpl.update(finalRequest, 1L);
        });

        //Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("Update Game Coin Offer")
    void WhenUpdateAGuideButErrorOcurredWhileUpdating(){
        //Arrange
        GameCoinOfferRequest request = new GameCoinOfferRequest();
        request.setAvaible(true);
        request.setCreatedAt(new Date());
        request.setDescription("Promocion 100% DCTO");
        request.setName("TeslaCoin");
        request.setImage("Url2");
        request.setPoint(50L);
        request.setPrice(30.0);

        when(gameCoinOfferRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        when(gameCoinOfferRepository.getGameCoinOfferById(1L)).thenReturn(Optional.of(gamecoinoffer1));

        //Act
        final GameCoinOfferRequest finalRequest = request;
        String message = "Error ocurred while updating game coin offer";
        Throwable exception = catchThrowable(() -> {
            gameCoinServiceImpl.update(finalRequest, 1L);
        });

        //Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("Delete Game Coin Offer")
    void DeleteGameCoinOffer(){
        // Arrange
        doNothing().when(gameCoinOfferRepository).deleteById(1L);

        // Act
        Throwable exception = catchThrowable(() -> {
            gameCoinServiceImpl.delete(1L);
        });

        // Assert
        assertThat(exception).isEqualTo(null);
    }

    @Test
    @DisplayName("When Delete An User But Error Ocurred While Deleting")
    public void WhenDeleteByIdButErrorOcurredWhileDeleting() {
        // Arrange
        doThrow(ResourceNotFoundExceptionRequest.class).when(gameCoinOfferRepository).deleteById(1L);

        // Act
        String message = "Error ocurred while deleting game coin offer";
        Throwable exception = catchThrowable(() -> {
            gameCoinServiceImpl.delete(1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);

    }
}