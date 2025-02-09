package com.example.game.service_api.controller;

import com.example.game.service_api.commons.constants.ApiPathVariables;
import com.example.game.service_api.commons.entities.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping(ApiPathVariables.API_ROUTE + ApiPathVariables.GAME_ROUTE)
public interface GameApi {
    @PostMapping ("/create")
    ResponseEntity<Game> createGame(
            @RequestHeader("createdByUserRequest") String createdByUser,
            @RequestBody Game game);

    @GetMapping("/all")
    ResponseEntity<List<Game>> getAllGames();

    @GetMapping("/details")
    ResponseEntity<Object> getGame(
            @RequestParam(required = false) String gameId,
            @RequestParam(required = false) String gameName,
            @RequestParam(required = false) String createdByUser,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String platforms,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) String updatedByUser,
            @RequestParam(required = false) Date createdAt,
            @RequestParam(required = false) Date updatedAt);

    @PutMapping("/update")
    ResponseEntity<Object> updateGame(
            @RequestHeader("updatedByUserRequest") String updatedByUser,
            @RequestParam(required = false) String gameId,
            @RequestParam(required = false) String gameName,
            @RequestParam(required = false) String createdByUser,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String platforms,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Date createdAt,
            @RequestParam(required = false) Date updatedAt,
            @RequestBody(required = false) Game game);

    @DeleteMapping("/delete")
    ResponseEntity<Object> deleteGame(
            @RequestParam(required = false) String gameId,
            @RequestParam(required = false) String gameName,
            @RequestParam(required = false) String createdByUser,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String platforms,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) String updatedByUser,
            @RequestParam(required = false) Date createdAt,
            @RequestParam(required = false) Date updatedAt);
}