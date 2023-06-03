package com.game_coin.payments.service;


import com.game_coin.payments.dto.OrderGameCoinRequest;
import com.game_coin.payments.dto.OrderGameCoinResponse;

import java.util.List;

public interface OrderGameCoinService {
    List<OrderGameCoinResponse> getAll();

    OrderGameCoinResponse getById(Long id);

    OrderGameCoinResponse create(OrderGameCoinRequest request);

    OrderGameCoinResponse update(OrderGameCoinRequest request, Long id);

    void delete(Long id);
}
