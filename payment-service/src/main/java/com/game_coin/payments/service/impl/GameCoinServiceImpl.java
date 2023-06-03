package com.game_coin.payments.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.game_coin.exception.ResourceNotFoundExceptionRequest;
import com.game_coin.payments.dto.GameCoinOfferRequest;
import com.game_coin.payments.dto.GameCoinOfferResponse;
import com.game_coin.payments.entity.GameCoinOffer;
import com.game_coin.payments.repository.GameCoinOfferRepository;
import com.game_coin.payments.service.GameCoinOfferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameCoinServiceImpl implements GameCoinOfferService {

    @Autowired
    private GameCoinOfferRepository gameCoinOfferRepository;

    private GameCoinOfferResponse convertToResponse(GameCoinOffer entity) {
        GameCoinOfferResponse response = new GameCoinOfferResponse();
        response.setCreatedAt(entity.getCreatedAt());
        response.setDescription(entity.getDescription());
        response.setId(entity.getId());
        response.setImage(entity.getImage());
        response.setName(entity.getName());
        response.setPoint(entity.getPoint());
        response.setPrice(entity.getPrice());
        response.setAvaible(entity.getAvaible());

        return response;
    }

    private GameCoinOffer convertToEntity(GameCoinOfferRequest request) {
        GameCoinOffer gameCoinOffer = new GameCoinOffer();
        gameCoinOffer.setDescription(request.getDescription());
        gameCoinOffer.setImage(request.getImage());
        gameCoinOffer.setName(request.getName());
        gameCoinOffer.setPoint(request.getPoint());
        gameCoinOffer.setPrice(request.getPrice());
        gameCoinOffer.setCreatedAt(request.getCreatedAt());
        gameCoinOffer.setAvaible(request.getAvaible());

        return gameCoinOffer;
    }

    private GameCoinOffer convertToEntity(GameCoinOfferRequest request, Long id) {
        GameCoinOffer gameCoinOffer = new GameCoinOffer();
        gameCoinOffer.setDescription(request.getDescription());
        gameCoinOffer.setId(id);
        gameCoinOffer.setImage(request.getImage());
        gameCoinOffer.setName(request.getName());
        gameCoinOffer.setPoint(request.getPoint());
        gameCoinOffer.setPrice(request.getPrice());
        gameCoinOffer.setCreatedAt(request.getCreatedAt());
        gameCoinOffer.setAvaible(request.getAvaible());

        return gameCoinOffer;
    }

    @Override
    public List<GameCoinOfferResponse> getAll() {
        var entities = gameCoinOfferRepository.findAll();
        var response = entities.stream().map(entity -> convertToResponse(entity)).collect(Collectors.toList());
        return response;
    }

    @Override
    public GameCoinOfferResponse getById(Long id) {
        var entity = gameCoinOfferRepository.getGameCoinOfferById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Game coin offer not found"));
        var response = convertToResponse(entity);
        return response;
    }

    @Override
    public GameCoinOfferResponse create(GameCoinOfferRequest request) {
        var entity = convertToEntity(request);

        try {
            gameCoinOfferRepository.save(entity);
            var response = convertToResponse(entity);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating game coin offer");
        }
    }

    @Override
    public GameCoinOfferResponse update(GameCoinOfferRequest request, Long id) {
        var entity = gameCoinOfferRepository.getGameCoinOfferById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Game coin offer not found"));
        entity = convertToEntity(request, id);

        try {
            gameCoinOfferRepository.save(entity);
            var response = convertToResponse(entity);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating game coin offer");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            gameCoinOfferRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting game coin offer");
        }
    }

}
