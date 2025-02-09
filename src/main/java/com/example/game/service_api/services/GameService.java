package com.example.game.service_api.services;

import com.example.game.service_api.commons.entities.Game;

import java.util.Date;
import java.util.List;

public interface GameService {
    Game createGame(Game gameRequest);

    List<Game> getAllGames();

    Object getGame(String gameId, String gameName, String createdByUser, String genre, String platforms,
                   Integer releaseYear, String company, Double rating, Double price, String updatedByUser, Date createdAt,
                   Date updatedAt);

    Object updateGame(String gameId, String gameName, String createdByUser, String genre, String platforms,
                    Integer releaseYear, String company, Double rating, Double price, String updatedByUser, Date createdAt,
                      Date updatedAt, Game game);

    Object deleteGame(String gameId, String gameName, String createdByUser, String genre, String platforms,
                      Integer releaseYear, String company, Double rating, Double price, String updatedByUser, Date createdAt,
                      Date updatedAt);
}