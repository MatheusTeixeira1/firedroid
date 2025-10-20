package br.com.firedroid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.firedroid.entity.GameDistributors;
import br.com.firedroid.entity.Game;

@Repository
public interface GameDistributorsRepository extends JpaRepository<GameDistributors, Long> {

    // Verifica se já existe um distribuidor com a mesma ordem dentro do mesmo jogo
    boolean existsByGameAndDisplayOrder(Game game, Integer displayOrder);
}
