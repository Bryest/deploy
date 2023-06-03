package com.game_coin.payments.repository;

import com.game_coin.payments.entity.DetailCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetailCardRepository extends JpaRepository<DetailCard, Long> {
    Optional<DetailCard> getDetailCardById(Long id);

    List<DetailCard> findAllByUserId(Long id);
}
