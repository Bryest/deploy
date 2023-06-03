package com.game_coin.payments.dto;

import com.game_coin.payments.model.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDetailCardResponse {
    List<DetailCardResponse> detailCardResponse;
    User user;
}
