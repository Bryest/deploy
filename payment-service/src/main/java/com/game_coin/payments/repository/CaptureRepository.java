package com.game_coin.payments.repository;

import com.game_coin.payments.entity.Capture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaptureRepository extends JpaRepository<Capture, Long> {

}
