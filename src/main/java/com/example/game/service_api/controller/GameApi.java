package com.example.game.service_api.controller;

import com.example.game.service_api.commons.constants.ApiPathVariables;
import com.example.game.service_api.commons.dto.GamePostRequest;
import com.example.game.service_api.commons.dto.GamePutRequest;
import com.example.game.service_api.commons.entities.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping(ApiPathVariables.API_ROUTE + ApiPathVariables.GAME_ROUTE)
public interface GameApi {

    @PostMapping ("/create")
    ResponseEntity<Game> createGame(
            @RequestHeader("creatorUserIdRequest") Long creatorUserId,
            @RequestHeader("creatorUsername") String creatorUsername,
            @RequestBody GamePostRequest gamePostRequest);


    @GetMapping("/all")
    ResponseEntity<List<Game>> getAllGames();

    @GetMapping("/details")
    ResponseEntity<Object> getGame(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long creatorUserId,
            @RequestParam(required = false) String creatorUsername,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String platforms,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Long updatorUserId,
            @RequestParam(required = false) String updatorUsername,
            @RequestParam(required = false) Date createdAt,
            @RequestParam(required = false) Date updatedAt);

    @PutMapping("/update")
    ResponseEntity<Object> updateGame(
            @RequestHeader("updatorUserIdRequest") Long updatorUserId,
            @RequestHeader("updatorUsernameRequest") String updatorUsername,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long creatorUserId,
            @RequestParam(required = false) String creatorUsername,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String platforms,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Date createdAt,
            @RequestParam(required = false) Date updatedAt,
            @RequestBody (required = false) GamePutRequest gamePutRequest);

    @DeleteMapping("/delete")
    ResponseEntity<Object> deleteGame(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long creatorUserId,
            @RequestParam(required = false) String creatorUsername,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String platforms,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Long updatorUserId,
            @RequestParam(required = false) String updatorUsername,
            @RequestParam(required = false) Date createdAt,
            @RequestParam(required = false) Date updatedAt);
}