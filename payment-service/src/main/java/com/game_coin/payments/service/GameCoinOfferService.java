package com.game_coin.payments.service;

import java.util.List;

import com.game_coin.payments.dto.GameCoinOfferRequest;
import com.game_coin.payments.dto.GameCoinOfferResponse;

public interface GameCoinOfferService {

    List<GameCoinOfferResponse> getAll();

    GameCoinOfferResponse getById(Long id);

    GameCoinOfferResponse create(GameCoinOfferRequest request);

    GameCoinOfferResponse update(GameCoinOfferRequest request, Long id);

    void delete(Long id);

}
