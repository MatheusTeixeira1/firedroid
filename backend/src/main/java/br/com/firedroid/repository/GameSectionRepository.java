package br.com.firedroid.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.firedroid.entity.Game;
import br.com.firedroid.entity.GameSection;

public interface GameSectionRepository extends JpaRepository<GameSection, Long> {
	boolean existsByGameAndDisplayOrder(Game game, int displayOrder);
}