package com.example.game.service_api.services;

import com.example.game.service_api.commons.entities.Game;

import java.util.Date;
import java.util.List;

public interface GameService {
    Game createGame(Game gameRequest);

    List<Game> getAllGames();

    Object getGame(Long id, String name, Long creatorUserId, String creatorUsername, String genre, String platforms,
                   Integer releaseYear, String company, Double rating, Double price, Long updatorUserId, String updatorUsername,
                   Date createdAt, Date updatedAt);

    Object updateGame(Long id, String name, Long creatorUserId, String creatorUsername, String genre, String platforms,
                    Integer releaseYear, String company, Double rating, Double price, Long updatorUserId, String updatorUsername,
                      Date createdAt, Date updatedAt, Game game);

    Object deleteGame(Long id, String name, Long creatorUserId, String creatorUsername, String genre, String platforms,
                      Integer releaseYear, String company, Double rating, Double price, Long updatorUserId, String updatorUsername,
                      Date createdAt, Date updatedAt);
}