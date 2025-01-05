package com.example.game.service_api.repositories;

import com.example.game.service_api.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
