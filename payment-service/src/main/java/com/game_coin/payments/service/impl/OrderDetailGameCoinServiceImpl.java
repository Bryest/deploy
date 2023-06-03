package com.game_coin.payments.service.impl;

import com.game_coin.exception.ResourceNotFoundExceptionRequest;
import com.game_coin.payments.client.GameCoinClient;
import com.game_coin.payments.client.UserClient;
import com.game_coin.payments.dto.*;
import com.game_coin.payments.entity.OrderDetailGameCoin;
import com.game_coin.payments.entity.OrderDetailGameCoinId;
import com.game_coin.payments.entity.OrderGameCoin;
import com.game_coin.payments.model.User;
import com.game_coin.payments.repository.OrderDetailGameCoinRepository;
import com.game_coin.payments.repository.OrderGameCoinRepository;
import com.game_coin.payments.service.OrderDetailGameCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailGameCoinServiceImpl implements OrderDetailGameCoinService {

    @Autowired
    private OrderDetailGameCoinRepository orderDetailGameCoinRepository;

    @Autowired
    private OrderGameCoinRepository orderGameCoinRepository;

    @Qualifier("com.game_coin.payments.client.UserClient")
    @Autowired
    private UserClient userClient;

    @Qualifier("com.game_coin.payments.client.GameCoinClient")
    @Autowired
    private GameCoinClient gameCoinClient;

    public OrderDetailGameCoinServiceImpl(OrderDetailGameCoinRepository orderDetailGameCoinRepository, OrderGameCoinRepository orderGameCoinRepository, UserClient userClient, GameCoinClient gameCoinClient) {
        this.orderDetailGameCoinRepository = orderDetailGameCoinRepository;
        this.orderGameCoinRepository = orderGameCoinRepository;
        this.userClient = userClient;
        this.gameCoinClient = gameCoinClient;
    }

    private OrderDetailSimpleResponse convertResponseSimple(OrderDetailGameCoin entity) {
        OrderDetailSimpleResponse response = new OrderDetailSimpleResponse();
        response.setCustomerId(entity.getOrderGameCoin().getCustomerId());
        response.setGameCoinId(entity.getOrderDetailGameCoinId().getGameCoinId());
        response.setOrderId(entity.getOrderGameCoin().getId());
        response.setQuantify(entity.getQuantify());
        response.setSubtotal(entity.getSubtotal());

        return response;
    }

    private DetailGameCoinResponse convertToResponseDetail(OrderDetailGameCoin entity) {
        DetailGameCoinResponse response = new DetailGameCoinResponse();
        response.setGameCoinId(entity.getOrderDetailGameCoinId().getGameCoinId());
        response.setQuantify(entity.getQuantify());
        response.setSubtotal(entity.getSubtotal());

        return response;
    }

    private OrderGameCoinResponse convertToResponseOrder(OrderGameCoin entity) {
        OrderGameCoinResponse response = new OrderGameCoinResponse();
        response.setCustomerId(entity.getCustomerId());
        response.setId(entity.getId());
        response.setSaleOrder(entity.getSaleOrder());
        response.setTotalPrice(entity.getTotalPrice());

        return response;
    }

    @Override
    public List<OrderDetailSimpleResponse> getAll() {
        var entities = orderDetailGameCoinRepository.findAll();
        var response = entities.stream().map(entity -> convertResponseSimple(entity)).collect(Collectors.toList());
        return response;
    }

    @Override
    public OrderDetailGameCoinResponse getAllByOrderId(Long id) {
        var entities = orderDetailGameCoinRepository.getAllByOrderId(id);

        if (entities.size() == 0) {
            throw new ResourceNotFoundExceptionRequest("Order detail is empty");
        }

        OrderDetailGameCoinResponse response = new OrderDetailGameCoinResponse();
        List<DetailGameCoinResponse> detailGameCoin = new ArrayList<DetailGameCoinResponse>();

        for (OrderDetailGameCoin entity : entities) {

            var gameCoinId = entity.getOrderDetailGameCoinId().getGameCoinId();

            var gameCoin = gameCoinClient.getById(gameCoinId).getBody();

            var detailResponse = convertToResponseDetail(entity);

            detailResponse.setGameCoin(gameCoin);

            detailGameCoin.add(detailResponse);
        }

        var order = entities.get(0).getOrderGameCoin();

        response.setLCoinResponses(detailGameCoin);
        response.setOrderGameCoinResponse(convertToResponseOrder(order));
        response.setUserId(order.getCustomerId());

        return response;
    }

    @Override
    public OrdeDetailGameCoinPayment create(OrderDetailGameCoinRequest request) {

        if (request.getLCoinRequests().size() == 0) {
            throw new ResourceNotFoundExceptionRequest("No items");
        }

        OrderGameCoin orderGameCoin = new OrderGameCoin();
        orderGameCoin.setCustomerId(request.getUserId());
        orderGameCoin.setSaleOrder(request.getSaleOrder());

        User user = userClient.getById(request.getUserId()).getBody();

        if (user.getId() == null) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred when found by user id");
        }

        try {
            orderGameCoinRepository.save(orderGameCoin);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating order game coin");
        }

        OrdeDetailGameCoinPayment response = new OrdeDetailGameCoinPayment();
        List<DetailGameCoinResponse> detailGameCoin = new ArrayList<DetailGameCoinResponse>();
        OrderDetailGameCoinId orderDetailGameCoinId = new OrderDetailGameCoinId();
        OrderDetailGameCoin entity = new OrderDetailGameCoin();

        var total = 0.0;

        for (DetailGameCoinRequest coinRequest : request.getLCoinRequests()) {
            orderDetailGameCoinId.setGameCoinId(coinRequest.getGameCoinId());
            orderDetailGameCoinId.setOrderId(orderGameCoin.getId());

            var gameCoin = gameCoinClient.getById(coinRequest.getGameCoinId()).getBody();

            if (gameCoin.getId() == null) {
                throw new ResourceNotFoundExceptionRequest("Error ocurred when found by game coin id");
            } else if (gameCoin.getAvaible() == false) {
                throw new ResourceNotFoundExceptionRequest("The offer not avaible");
            }

            entity.setOrderDetailGameCoinId(orderDetailGameCoinId);
            entity.setOrderGameCoin(orderGameCoin);
            entity.setQuantify(coinRequest.getQuantify());

            var subTotal = coinRequest.getQuantify() * gameCoin.getPrice();

            entity.setSubtotal(subTotal);

            var responseDetail = convertToResponseDetail(entity);
            responseDetail.setGameCoin(gameCoin);

            detailGameCoin.add(responseDetail);

            try {
                orderDetailGameCoinRepository.save(entity);
            } catch (Exception e) {
                throw new ResourceNotFoundExceptionRequest("Error ocurred while creating order detail game coin");
            }

            total = total + subTotal;
        }

        response.setLCoinResponses(detailGameCoin);
        response.setUserId(request.getUserId());
        response.setOrderGameCoinResponse(convertToResponseOrder(orderGameCoin));
        response.setTotal(total);
        response.setUser(user);

        return response;
    }

    @Override
    public void deleteByOrderId(Long id) {
        try {
            orderDetailGameCoinRepository.deleteAllByOrderId(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting order detail game coin");
        }

        try {
            orderGameCoinRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting order game coin");
        }
    }

}
