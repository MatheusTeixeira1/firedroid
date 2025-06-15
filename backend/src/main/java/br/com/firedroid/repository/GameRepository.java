package br.com.firedroid.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.firedroid.entity.Game;

public interface GameRepository extends JpaRepository<Game, Long>{
	List<Game> findByPageThemeId(Long pageThemeId);
}
