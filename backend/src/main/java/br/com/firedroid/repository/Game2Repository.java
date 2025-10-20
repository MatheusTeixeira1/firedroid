package br.com.firedroid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.firedroid.entity.Game;

@Repository
public interface Game2Repository extends JpaRepository<Game, Long> {
    // Se precisar, pode adicionar consultas customizadas aqui
    // Exemplo: buscar por nome
    // Optional<Game2> findByName(String name);
}
