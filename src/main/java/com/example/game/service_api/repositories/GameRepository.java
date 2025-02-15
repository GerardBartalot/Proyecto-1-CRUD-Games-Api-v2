package com.example.game.service_api.repositories;

import com.example.game.service_api.commons.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByName(String name);
    List<Game> findByCreatorUserId(Long creatorUserId);
    List<Game> findByCreatorUsername(String creatorUsername);
    List<Game> findByGenre(String genre);
    List<Game> findByPlatforms(String platforms);
    List<Game> findByReleaseYear(Integer releaseYear);
    List<Game> findByCompany(String company);
    List<Game> findByRating(Double rating);
    List<Game> findByPrice(Double price);
    List<Game> findByUpdatorUserId(Long updatorUserId);
    List<Game> findByUpdatorUsername(String updatorUsername);
    List<Game> findByCreatedAt(Date createdAt);
    List<Game> findByUpdatedAt(Date updatedAt);

}