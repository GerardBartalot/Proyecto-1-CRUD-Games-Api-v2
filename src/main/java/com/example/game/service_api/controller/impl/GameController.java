package com.example.game.service_api.controller.impl;

import com.example.game.service_api.commons.dto.GamePostRequest;
import com.example.game.service_api.commons.dto.GamePutRequest;
import com.example.game.service_api.commons.entities.Game;
import org.modelmapper.ModelMapper;
import com.example.game.service_api.controller.GameApi;
import com.example.game.service_api.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class GameController implements GameApi {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<Game> createGame(Long creatorUserId, String creatorUsername,
                                           GamePostRequest gamePostRequest) {
        Game game = modelMapper.map(gamePostRequest, Game.class);
        game.setCreatorUserId(creatorUserId);
        game.setCreatorUsername(creatorUsername);
        Game gameCreated = this.gameService.createGame(game);
        return ResponseEntity.ok(gameCreated);
    }

    @Override
    public ResponseEntity<List<Game>> getAllGames() {
        return ResponseEntity.ok(this.gameService.getAllGames());
    }

    @Override
    public ResponseEntity<Object> getGame(Long id, String name, Long creatorUserId, String creatorUsername, String genre,
                                          String platforms, Integer releaseYear, String company, Double rating,
                                          Double price, Long updatorUserId, String updatorUsername, Date createdAt,
                                          Date updatedAt) {
        return ResponseEntity.ok(this.gameService.getGame(id, name, creatorUserId, creatorUsername, genre, platforms,
                                                        releaseYear, company, rating, price, updatorUserId, updatorUsername,
                                                        createdAt, updatedAt));
    }

    @Override
    public ResponseEntity<Object> updateGame(Long updatorUserId, String updatorUsername, Long id, String name,
                                             Long creatorUserId, String creatorUsername, String genre, String platforms,
                                             Integer releaseYear, String company, Double rating, Double price,
                                             Date createdAt, Date updatedAt, GamePutRequest gamePutRequest) {
        Game game = modelMapper.map(gamePutRequest, Game.class);
        game.setUpdatorUserId(creatorUserId);
        game.setUpdatorUsername(creatorUsername);

        return ResponseEntity.ok(this.gameService.updateGame(
                id, name, creatorUserId, creatorUsername, genre, platforms, releaseYear, company, rating, price,
                updatorUserId, updatorUsername, createdAt, updatedAt, game
        ));
    }

    @Override
    public ResponseEntity<Object> deleteGame(Long id, String name, Long creatorUserId, String creatorUsername,
                                                      String genre, String platforms, Integer releaseYear,
                                                      String company, Double rating, Double price,
                                                      Long updatorUserId, String updatorUsername, Date createdAt, Date updatedAt) {
        Game deletedGame = (Game) gameService.deleteGame(id, name, creatorUserId, creatorUsername, genre, platforms,
                                                        releaseYear, company, rating, price, updatorUserId, updatorUsername,
                                                        createdAt, updatedAt);
        return ResponseEntity.ok(deletedGame);
    }
}

