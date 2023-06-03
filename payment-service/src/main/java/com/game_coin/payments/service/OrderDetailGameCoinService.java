package com.game_coin.payments.service;


import com.game_coin.payments.dto.OrdeDetailGameCoinPayment;
import com.game_coin.payments.dto.OrderDetailGameCoinRequest;
import com.game_coin.payments.dto.OrderDetailGameCoinResponse;
import com.game_coin.payments.dto.OrderDetailSimpleResponse;

import java.util.List;

public interface OrderDetailGameCoinService {

    List<OrderDetailSimpleResponse> getAll();

    OrdeDetailGameCoinPayment create(OrderDetailGameCoinRequest request);

    OrderDetailGameCoinResponse getAllByOrderId(Long id);

    void deleteByOrderId(Long id);

}
