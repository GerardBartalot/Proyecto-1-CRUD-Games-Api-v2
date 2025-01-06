package com.example.game.service_api.services;

import com.example.game.service_api.commons.entities.Game;

public interface GameService {
    Game saveGame(Game gameRequest);

    Game getGameById(String id);
}
