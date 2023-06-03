package com.game_coin.payments.repository;

import com.game_coin.payments.entity.OrderGameCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderGameCoinRepository extends JpaRepository<OrderGameCoin, Long> {
    Optional<OrderGameCoin> getOrderGameById(Long id);
}
