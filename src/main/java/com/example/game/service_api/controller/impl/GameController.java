package com.example.game.service_api.controller.impl;

import com.example.game.service_api.commons.entities.Game;
import com.example.game.service_api.controller.GameApi;
import com.example.game.service_api.services.GameService;
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

    @Override
    public ResponseEntity<Game> createGame(String createdByUser, Game game) {
        game.setCreatedByUser(createdByUser);
        Game gameCreated = this.gameService.createGame(game);
        return ResponseEntity.ok(gameCreated);
    }

    @Override
    public ResponseEntity<List<Game>> getAllGames() {
        return ResponseEntity.ok(this.gameService.getAllGames());
    }

    @Override
    public ResponseEntity<Object> getGame(String gameId, String gameName, String createdByUser, String genre,
                                          String platforms, Integer releaseYear, String company, Double rating,
                                          Double price, String updatedByUser, Date createdAt, Date updatedAt) {
        return ResponseEntity.ok(this.gameService.getGame(gameId, gameName, createdByUser, genre, platforms,
                                                        releaseYear, company, rating, price, updatedByUser,
                                                        createdAt, updatedAt));
    }

    @Override
    public ResponseEntity<Object> updateGame(String updatedByUser, String gameId, String gameName, String createdByUser,
                                                    String genre, String platforms, Integer releaseYear,
                                                    String company, Double rating, Double price,
                                                    Date createdAt, Date updatedAt, Game game) {
        game.setUpdatedByUser(updatedByUser);

        return ResponseEntity.ok(this.gameService.updateGame(
                gameId, gameName, createdByUser, genre, platforms, releaseYear, company, rating, price,
                updatedByUser, createdAt, updatedAt, game
        ));
    }

    @Override
    public ResponseEntity<Object> deleteGame(String gameId, String gameName, String createdByUser,
                                                      String genre, String platforms, Integer releaseYear,
                                                      String company, Double rating, Double price,
                                                      String updatedByUser, Date createdAt, Date updatedAt) {
        Game deletedGame = (Game) gameService.deleteGame(gameId, gameName, createdByUser, genre, platforms,
                                                        releaseYear, company, rating, price, updatedByUser,
                                                        createdAt, updatedAt);
        return ResponseEntity.ok(deletedGame);
    }
}

