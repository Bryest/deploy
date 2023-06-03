package com.game_coin.payments.service.impl;

import com.game_coin.exception.ResourceNotFoundExceptionRequest;
import com.game_coin.payments.client.UserClient;
import com.game_coin.payments.dto.DetailCardRequest;
import com.game_coin.payments.dto.DetailCardResponse;
import com.game_coin.payments.dto.UserDetailCardResponse;
import com.game_coin.payments.entity.DetailCard;
import com.game_coin.payments.model.User;
import com.game_coin.payments.repository.DetailCardRepository;
import com.game_coin.payments.service.DetailCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetailCardServiceImpl implements DetailCardService {

    @Autowired
    private DetailCardRepository detailCardRepository;

    @Qualifier("com.game_coin.payments.client.UserClient")
    @Autowired
    private UserClient userClient;

    private DetailCardResponse convertToResponse(DetailCard entity) {
        DetailCardResponse response = new DetailCardResponse();
        response.setCardholder(entity.getCardholder());
        response.setCode(entity.getCode());
        response.setDay(entity.getDay());
        response.setId(entity.getId());
        response.setMonth(entity.getMonth());
        response.setObfuscatedCard(entity.getObfuscateCard());
        response.setUserId(entity.getUserId());
        return response;
    }

    private DetailCard convertToEntity(DetailCardRequest request) {
        DetailCard entity = new DetailCard();
        entity.setCardholder(request.getCardholder());
        entity.setCode(request.getCode());
        entity.setDay(request.getDay());
        entity.setMonth(request.getMonth());
        entity.setObfuscateCard(request.getObfuscatedCard());
        entity.setUserId(request.getUserId());
        return entity;
    }

    private DetailCard convertToEntity(DetailCardRequest request, Long id) {
        DetailCard entity = new DetailCard();
        entity.setCardholder(request.getCardholder());
        entity.setCode(request.getCode());
        entity.setDay(request.getDay());
        entity.setMonth(request.getMonth());
        entity.setObfuscateCard(request.getObfuscatedCard());
        entity.setUserId(request.getUserId());
        entity.setId(id);
        return entity;
    }

    @Override
    public List<DetailCardResponse> getAll() {
        var entities = detailCardRepository.findAll();
        var response = entities.stream().map(entity -> convertToResponse(entity)).collect(Collectors.toList());
        return response;
    }

    @Override
    public UserDetailCardResponse getAllByUserId(Long id) {
        var entities = detailCardRepository.findAllByUserId(id);
        var detailCardResponse = entities.stream().map(entity -> convertToResponse(entity))
                .collect(Collectors.toList());

        User user = userClient.getById(id).getBody();

        UserDetailCardResponse response = new UserDetailCardResponse();
        response.setDetailCardResponse(detailCardResponse);
        response.setUser(user);
        return response;
    }

    @Override
    public DetailCardResponse getById(Long id) {
        var entity = detailCardRepository.getDetailCardById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Card not found"));
        var response = convertToResponse(entity);
        return response;
    }

    @Override
    public DetailCardResponse create(DetailCardRequest request) {
        var entity = convertToEntity(request);

        var user = userClient.getById(request.getUserId()).getBody();

        try {
            detailCardRepository.save(entity);
            var response = convertToResponse(entity);
            response.setUser(user);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating card");
        }
    }

    @Override
    public DetailCardResponse update(DetailCardRequest request, Long id) {
        var entity = detailCardRepository.getDetailCardById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Card not found"));
        entity = convertToEntity(request, id);

        var user = userClient.getById(request.getUserId()).getBody();

        try {
            detailCardRepository.save(entity);
            var response = convertToResponse(entity);
            response.setUser(user);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating card");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            detailCardRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting card");
        }
    }

}
