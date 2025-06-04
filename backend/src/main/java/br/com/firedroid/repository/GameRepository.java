package br.com.firedroid.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.firedroid.entity.Game;

public interface GameRepository extends JpaRepository<Game, Long>{
}
