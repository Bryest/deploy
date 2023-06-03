package com.game_coin.payments.service;


import com.game_coin.payments.entity.Capture;

import java.util.List;

public interface CaptureService {
    Capture cancel(String token);

    Capture accept(String paymentId, String token, String payerId);

    List<Capture> getAll();
}
