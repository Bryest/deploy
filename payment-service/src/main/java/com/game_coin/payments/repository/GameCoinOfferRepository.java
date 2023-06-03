package com.game_coin.payments.repository;

import java.util.Optional;

import com.game_coin.payments.entity.GameCoinOffer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCoinOfferRepository extends JpaRepository<GameCoinOffer, Long> {

    Optional<GameCoinOffer> getGameCoinOfferById(Long id);

}
