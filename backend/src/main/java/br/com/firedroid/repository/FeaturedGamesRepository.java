package br.com.firedroid.repository;

import java.util.Optional;
import br.com.firedroid.entity.FeaturedGames;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeaturedGamesRepository extends JpaRepository<FeaturedGames, Long> {
    Optional<FeaturedGames> findTopByOrderByCreatedAtDesc();
}
