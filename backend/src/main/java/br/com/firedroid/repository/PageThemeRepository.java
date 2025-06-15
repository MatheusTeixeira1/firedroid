package br.com.firedroid.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.firedroid.entity.PageTheme;

public interface PageThemeRepository extends JpaRepository <PageTheme, Long>{
	boolean existsByName(String name);
//	PageTheme findByName(String name);
	Optional<PageTheme> findByName(String name);
}
