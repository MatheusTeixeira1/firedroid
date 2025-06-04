package br.com.firedroid.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.firedroid.entity.GameSection;
import br.com.firedroid.entity.GameSectionParallaxLayer;

public interface GameSectionParallaxLayerRepository  extends JpaRepository<GameSectionParallaxLayer, Long> {
	boolean existsByGameSectionAndDisplayOrder(GameSection section, int displayOrder);
}

