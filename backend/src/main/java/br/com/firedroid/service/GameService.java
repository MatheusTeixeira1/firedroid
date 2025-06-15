package br.com.firedroid.service;

import br.com.firedroid.DTOs.game.GameRequest;
import br.com.firedroid.DTOs.game.GameAdminResponse;
import br.com.firedroid.DTOs.game.GamePublicResponse;
import br.com.firedroid.entity.Game;
import br.com.firedroid.entity.PageTheme;
import br.com.firedroid.entity.User;
import br.com.firedroid.exception.CustomEntityNotFoundException;
import br.com.firedroid.exception.InvalidUsernameException;
import br.com.firedroid.repository.GameRepository;
import br.com.firedroid.repository.PageThemeRepository;
import br.com.firedroid.repository.UserRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PageThemeRepository pageThemeRepository;
	
	
	public GameService(GameRepository gameRepository, UserRepository userRepository) {
		this.gameRepository = gameRepository;
		this.userRepository = userRepository;
	}
	
	
	
	
	// ----- Para usuarios -----

	public List<GamePublicResponse> getAll() {
		List<Game> games = gameRepository.findAll();
		return games.stream().map(GamePublicResponse::fromEntity).toList();
	}

	public GamePublicResponse getById(Long id) {
		Game game = gameRepository.findById(id)
				.orElseThrow(() -> new CustomEntityNotFoundException("Jogo não encontrado"));

		return GamePublicResponse.fromEntity(game);
	}
	
	// ----- ----- ----- -----

	
	
	
	// ----- Para Funcionarios -----

	public List<GameAdminResponse> getAllAdmin() {
		List<Game> games = gameRepository.findAll();
		return games.stream().map(GameAdminResponse::fromEntity).toList();
	}

	public GameAdminResponse getByIdAdmin(Long id) {
		Game game = gameRepository.findById(id)
				.orElseThrow(() -> new CustomEntityNotFoundException("Jogo não encontrado"));
		return GameAdminResponse.fromEntity(game);
	}

	public void create(GameRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Usuario %s não encontrado", username)));
		Game game = new Game();
		
		game.setName(request.name());
		game.setDescription(request.description());
		game.setImage(request.imageUrl());
		game.setSteamLink(request.steamLink());
		game.setEpicLink(request.epicLink());
		game.setItchioLink(request.itchioLink());
		game.setSiteLink(request.siteLink());
		PageTheme defaultTheme = pageThemeRepository.findByName("default")
		        .orElseThrow(() -> new CustomEntityNotFoundException("O tema padrão do sistema não foi encontrado, crie um com o nome 'default'"));
		
		game.setPageTheme(defaultTheme);
		game.setCreatedBy(user);

		gameRepository.save(game);
	}

	public void update(Long id, GameRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new InvalidUsernameException(String.format("Usuario %s invalido.", username)));
		Game game = gameRepository.findById(id)
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Jogo de id %s não foi encontrado.", id)));
		game.setName(request.name());
		game.setDescription(request.description());
		game.setImage(request.imageUrl());
		game.setSteamLink(request.steamLink());
		game.setEpicLink(request.epicLink());
		game.setItchioLink(request.itchioLink());
		game.setSiteLink(request.siteLink());
		game.setUpdatedBy(user);
		gameRepository.save(game);
	}
	
	public void chengeTheme(Long gameId, Long newThemeId) {
		Game game = gameRepository.findById(gameId)
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Jogo de id %s não foi encontrado.", gameId)));
		PageTheme theme = pageThemeRepository.findById(newThemeId)
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Tema de id %s não foi encontrado.", newThemeId)));
		game.setPageTheme(theme);
		gameRepository.save(game);
	}
	
	public void delete(Long id) {
		Game game = gameRepository.findById(id)
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Jogo de id %s não encontrado", id)));
		gameRepository.delete(game);
	}
}