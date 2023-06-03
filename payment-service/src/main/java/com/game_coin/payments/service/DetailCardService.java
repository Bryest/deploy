package com.game_coin.payments.service;


import com.game_coin.payments.dto.DetailCardRequest;
import com.game_coin.payments.dto.DetailCardResponse;
import com.game_coin.payments.dto.UserDetailCardResponse;

import java.util.List;

public interface DetailCardService {
    List<DetailCardResponse> getAll();

    UserDetailCardResponse getAllByUserId(Long id);

    DetailCardResponse getById(Long id);

    DetailCardResponse create(DetailCardRequest request);

    DetailCardResponse update(DetailCardRequest request, Long id);

    void delete(Long id);
}
