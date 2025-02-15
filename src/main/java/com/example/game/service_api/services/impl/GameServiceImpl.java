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
        if (gameRepository.findByName(gameRequest.getName()).isPresent()) {
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
    public Object getGame(Long id, String name, Long creatorUserId, String creatorUsername, String genre,
                          String platforms, Integer releaseYear, String company, Double rating,
                          Double price, Long updatorUserId, String updatorUsername, Date createdAt,
                          Date updatedAt) {
        if (id != null) {
            try {
                return gameRepository.findById(id)
                        .orElseThrow(() -> new GameException(HttpStatus.NOT_FOUND, "No games found with this id"));
            } catch (NumberFormatException e) {
                throw new GameException(HttpStatus.BAD_REQUEST, "Invalid game ID format");
            }
        }

        if (name != null) {
            return gameRepository.findByName(name)
                    .orElseThrow(() -> new GameException(HttpStatus.NOT_FOUND, "No games found with this name"));
        }

        // Si no se pasa ni ID ni Name, aplicar filtros
        if (creatorUserId == null && creatorUsername == null && genre == null && platforms == null && releaseYear == null
                && company == null && rating == null && updatorUserId == null && updatorUsername == null && createdAt == null
                && updatedAt == null) {
            throw new GameException(HttpStatus.BAD_REQUEST, "At least one filter must be provided");
        }

        List<Game> games = gameRepository.findAll();

        if (creatorUserId != null) {
            List<Game> filtered = gameRepository.findByCreatorUserId(creatorUserId);
            if (filtered.isEmpty()) {
                throw new GameException(HttpStatus.NOT_FOUND,("No games found with this creator name"));
            } else {
                games.retainAll(filtered);
            }
        }

        if (creatorUsername != null) {
            List<Game> filtered = gameRepository.findByCreatorUsername(creatorUsername);
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

        if (updatorUserId != null) {
            List<Game> filtered = gameRepository.findByUpdatorUserId(updatorUserId);
            if (filtered.isEmpty()) {
                throw new GameException(HttpStatus.NOT_FOUND,("No games found with this updater name"));
            } else {
                games.retainAll(filtered);
            }
        }

        if (updatorUsername != null) {
            List<Game> filtered = gameRepository.findByUpdatorUsername(updatorUsername);
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
    public Object updateGame(Long id, String name, Long creatorUserId, String creatorUsername, String genre, String platforms,
                             Integer releaseYear, String company, Double rating, Double price, Long updatorUserId,
                             String updatorUsername, Date createdAt, Date updatedAt, Game game) {

        Object result;

        // Si se pasa id o name, buscamos solo un juego.
        if (id != null || name != null) {
            result = getGame(id, name, null, null, null, null, null,
                    null, null, null, null, null, null, null);

            if (result instanceof Game existingGame) {
                updateOnlyProvidedFields(existingGame, creatorUserId, creatorUsername, genre, platforms, releaseYear, company,
                        rating, price, updatorUserId, updatorUsername, createdAt, updatedAt, game);
                return gameRepository.save(existingGame);
            } else {
                throw new GameException(HttpStatus.NOT_FOUND, "No game found with the given id or name.");
            }
        }

        // Si no se pasó id o name, buscamos múltiples juegos usando otros filtros.
        result = getGame(null, null, creatorUserId, creatorUsername, genre, platforms, releaseYear, company, rating,
                price, updatorUserId, updatorUsername, createdAt, updatedAt);

        if (result instanceof List) {
            List<Game> gamesToUpdate = (List<Game>) result;

            if (gamesToUpdate.isEmpty()) {
                throw new GameException(HttpStatus.NOT_FOUND, "No games found with the given filters.");
            }

            for (Game existingGame : gamesToUpdate) {
                updateOnlyProvidedFields(existingGame, creatorUserId, creatorUsername, genre, platforms, releaseYear, company,
                        rating, price, updatorUserId, updatorUsername, createdAt, updatedAt, game);
            }

            return gameRepository.saveAll(gamesToUpdate);
        }

        throw new GameException(HttpStatus.NOT_FOUND, "No games found to update.");
    }

    private void updateOnlyProvidedFields(Game existingGame, Long creatorUserId, String creatorUsername, String genre,
                                          String platforms, Integer releaseYear, String company, Double rating, Double price,
                                          Long updatorUserId, String updatorUsername, Date createdAt, Date updatedAt, Game game) {
        boolean updated = false;

        if (game.getName() != null) {
            existingGame.setName(game.getName());
            updated = true;
        }

        if (game.getCreatorUserId() != null) {
            existingGame.setCreatorUserId(game.getCreatorUserId());
            updated = true;
        }

        if (game.getCreatorUsername() != null) {
            existingGame.setCreatorUsername(game.getCreatorUsername());
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

        if (game.getUpdatorUserId() != null) {
            existingGame.setUpdatorUserId(game.getUpdatorUserId());
            updated = true;
        }

        if (game.getUpdatorUsername() != null) {
            existingGame.setUpdatorUsername(game.getUpdatorUsername());
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
    public Object deleteGame(Long id, String name, Long creatorUserId, String creatorUsername, String genre, String platforms,
                             Integer releaseYear, String company, Double rating, Double price, Long updatorUserId,
                             String updatorUsername, Date createdAt, Date updatedAt) {

        Object result = getGame(id, name, creatorUserId, creatorUsername, genre, platforms, releaseYear, company, rating,
                            price, updatorUserId, updatorUsername, createdAt, updatedAt);

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