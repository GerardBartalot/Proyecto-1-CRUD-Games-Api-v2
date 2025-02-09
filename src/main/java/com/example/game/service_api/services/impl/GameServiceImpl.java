package com.example.game.service_api.services.impl;

import com.example.game.service_api.commons.entities.Game;
import com.example.game.service_api.commons.exceptions.GameException;
import com.example.game.service_api.repositories.GameRepository;
import com.example.game.service_api.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game createGame(Game gameRequest) {
        if (gameRepository.findByGameName(gameRequest.getGameName()).isPresent()) {
            throw new GameException(HttpStatus.CONFLICT, "This game already exists");
        } else {
            return this.gameRepository.save(gameRequest);
        }
    }

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Object getGame(String gameId, String gameName, String createdByUser, String genre, String platforms,
                          Integer releaseYear, String company, Double rating, Double price, String updatedByUser,
                          Date createdAt, Date updatedAt) {
        if (gameId != null) {
            try {
                Long id = Long.parseLong(gameId);
                return gameRepository.findById(id)
                        .orElseThrow(() -> new GameException(HttpStatus.NOT_FOUND, "No games found with this id"));
            } catch (NumberFormatException e) {
                throw new GameException(HttpStatus.BAD_REQUEST, "Invalid game ID format");
            }
        }

        if (gameName != null) {
            return gameRepository.findByGameName(gameName)
                    .orElseThrow(() -> new GameException(HttpStatus.NOT_FOUND, "No games found with this name"));
        }

        // Si no se pasa ni ID ni Name, aplicar filtros
        if (createdByUser == null && genre == null && platforms == null && releaseYear == null && company == null
                && rating == null && updatedByUser == null && createdAt == null && updatedAt == null) {
            throw new GameException(HttpStatus.BAD_REQUEST, "At least one filter must be provided");
        }

        List<Game> games = gameRepository.findAll();

        if (createdByUser != null) {
            List<Game> filtered = gameRepository.findByCreatedByUser(createdByUser);
            if (filtered.isEmpty()) {
                throw new GameException(HttpStatus.NOT_FOUND,("No games found with this creator name"));
            } else {
                games.retainAll(filtered);
            }
        }

        if (genre != null) {
            List<Game> filtered = gameRepository.findByGenre(genre);
            if (filtered.isEmpty()) {
                throw new GameException(HttpStatus.NOT_FOUND,("No games found with this genre"));
            } else {
                games.retainAll(filtered);
            }
        }

        if (platforms != null && !platforms.isEmpty()) {
            List<Game> filtered = gameRepository.findByPlatforms(platforms);

            if (filtered.isEmpty()) {
                filtered = games.stream()
                        .filter(game -> Arrays.asList(game.getPlatforms().split(", ")).contains(platforms))
                        .collect(Collectors.toList());
            }

            if (filtered.isEmpty()) {
                throw new GameException(HttpStatus.NOT_FOUND,("No games found with this platform"));
            } else {
                games = filtered;
            }
        }

        if (releaseYear != null) {
            List<Game> filtered = gameRepository.findByReleaseYear(releaseYear);
            if (filtered.isEmpty()) {
                throw new GameException(HttpStatus.NOT_FOUND,("No games found with this release year"));
            } else {
                games.retainAll(filtered);
            }
        }

        if (company != null) {
            List<Game> filtered = gameRepository.findByCompany(company);
            if (filtered.isEmpty()) {
                throw new GameException(HttpStatus.NOT_FOUND,("No games found with this company"));
            } else {
                games.retainAll(filtered);
            }
        }

        if (rating != null) {
            List<Game> filtered = gameRepository.findByRating(rating);
            if (filtered.isEmpty()) {
                throw new GameException(HttpStatus.NOT_FOUND,("No games found with this rating"));
            } else {
                games.retainAll(filtered);
            }
        }

        if (price != null) {
            List<Game> filtered = gameRepository.findByPrice(price);
            if (filtered.isEmpty()) {
                throw new GameException(HttpStatus.NOT_FOUND,("No games found with this rating"));
            } else {
                games.retainAll(filtered);
            }
        }

        if (updatedByUser != null) {
            List<Game> filtered = gameRepository.findByUpdatedByUser(updatedByUser);
            if (filtered.isEmpty()) {
                throw new GameException(HttpStatus.NOT_FOUND,("No games found with this updater name"));
            } else {
                games.retainAll(filtered);
            }
        }

        if (createdAt != null) {
            List<Game> filtered = gameRepository.findByCreatedAt(createdAt);
            if (filtered.isEmpty()) {
                throw new GameException(HttpStatus.NOT_FOUND,("No games found with this creation date"));
            } else {
                games.retainAll(filtered);
            }
        }

        if (updatedAt != null) {
            List<Game> filtered = gameRepository.findByUpdatedAt(updatedAt);
            if (filtered.isEmpty()) {
                throw new GameException(HttpStatus.NOT_FOUND,("No games found with this update date"));
            } else {
                games.retainAll(filtered);
            }
        }

        if (games.isEmpty()) {
            throw new GameException(HttpStatus.NOT_FOUND, "No games found with the given criteria");
        }

        return games;
    }

    @Override
    public Object updateGame(String gameId, String gameName, String createdByUser, String genre, String platforms,
                             Integer releaseYear, String company, Double rating, Double price, String updatedByUser,
                             Date createdAt, Date updatedAt, Game game) {

        Object result;

        // 📌 Si se pasa gameId o gameName, buscamos solo un juego.
        if (gameId != null || gameName != null) {
            result = getGame(gameId, gameName, null, null, null, null, null, null
                    , null, null, null, null);

            if (result instanceof Game existingGame) {
                updateOnlyProvidedFields(existingGame, createdByUser, genre, platforms, releaseYear, company, rating,
                        price, updatedByUser, createdAt, updatedAt, game);
                return gameRepository.save(existingGame);
            } else {
                throw new GameException(HttpStatus.NOT_FOUND, "No game found with the given gameId or gameName.");
            }
        }

        // 📌 Si no se pasó gameId o gameName, buscamos múltiples juegos usando otros filtros.
        result = getGame(null, null, createdByUser, genre, platforms, releaseYear, company, rating,
                price, updatedByUser, createdAt, updatedAt);

        if (result instanceof List) {
            List<Game> gamesToUpdate = (List<Game>) result;

            if (gamesToUpdate.isEmpty()) {
                throw new GameException(HttpStatus.NOT_FOUND, "No games found with the given filters.");
            }

            for (Game existingGame : gamesToUpdate) {
                updateOnlyProvidedFields(existingGame, createdByUser, genre, platforms, releaseYear, company, rating,
                        price, updatedByUser, createdAt, updatedAt, game);
            }

            return gameRepository.saveAll(gamesToUpdate);
        }

        throw new GameException(HttpStatus.NOT_FOUND, "No games found to update.");
    }

    private void updateOnlyProvidedFields(Game existingGame, String createdByUser, String genre, String platforms,
                                          Integer releaseYear, String company, Double rating, Double price,
                                          String updatedByUser, Date createdAt, Date updatedAt, Game game) {
        boolean updated = false;

        if (game.getGameName() != null) {
            existingGame.setGameName(game.getGameName());
            updated = true;
        }

        if (game.getCreatedByUser() != null) {
            existingGame.setCreatedByUser(game.getCreatedByUser());
            updated = true;
        }

        if (game.getGenre() != null) {
            existingGame.setGenre(game.getGenre());
            updated = true;
        }

        if (game.getPlatforms() != null) {
            existingGame.setPlatforms(game.getPlatforms());
            updated = true;
        }

        if (game.getReleaseYear() != null) {
            existingGame.setReleaseYear(game.getReleaseYear());
            updated = true;
        }

        if (game.getCompany() != null) {
            existingGame.setCompany(game.getCompany());
            updated = true;
        }

        if (game.getRating() != null) {
            existingGame.setRating(game.getRating());
            updated = true;
        }

        if (game.getPrice() != null) {
            existingGame.setPrice(game.getPrice());
            updated = true;
        }

        if (game.getUpdatedByUser() != null) {
            existingGame.setUpdatedByUser(game.getUpdatedByUser());
            updated = true;
        }

        if (game.getCreatedAt() != null) {
            existingGame.setCreatedAt(game.getCreatedAt());
            updated = true;
        }

        if (game.getUpdatedAt() != null) {
            existingGame.setUpdatedAt(game.getUpdatedAt());
            updated = true;
        }
    }

    @Override
    public Object deleteGame(String gameId, String gameName, String createdByUser, String genre, String platforms,
                             Integer releaseYear, String company, Double rating, Double price, String updatedByUser,
                             Date createdAt, Date updatedAt) {

        Object result = getGame(gameId, gameName, createdByUser, genre, platforms, releaseYear, company, rating,
                            price, updatedByUser, createdAt, updatedAt);

        if (result instanceof Game gameToDelete) {
            gameRepository.delete(gameToDelete);
            return gameToDelete;
        } else {
            List<Game> gamesToDelete = (List<Game>) result;
            if (gamesToDelete.isEmpty()) {
                throw new GameException(HttpStatus.NOT_FOUND, "No games found to delete.");
            }
            gameRepository.deleteAll(gamesToDelete);
            return gamesToDelete;
        }
    }

}