package com.game_coin.payments.repository;

import com.game_coin.payments.entity.OrderDetailGameCoin;
import com.game_coin.payments.entity.OrderDetailGameCoinId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderDetailGameCoinRepository extends JpaRepository<OrderDetailGameCoin, OrderDetailGameCoinId> {

    @Query("delete from OrderDetailGameCoin o where o.orderGameCoin.id = :orderId")
    @Transactional
    @Modifying
    void deleteAllByOrderId(@Param("orderId") Long orderId);

    @Query("select o from OrderDetailGameCoin o where o.orderGameCoin.id = :orderId")
    @Transactional
    @Modifying
    List<OrderDetailGameCoin> getAllByOrderId(@Param("orderId") Long orderId);

}
