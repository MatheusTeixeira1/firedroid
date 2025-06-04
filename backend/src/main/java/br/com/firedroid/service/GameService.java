package br.com.firedroid.service;

import br.com.firedroid.DTOs.CreateGameRequest;
import br.com.firedroid.DTOs.GameAdminResponse;
import br.com.firedroid.DTOs.GamePublicResponse;
import br.com.firedroid.entity.Game;
import br.com.firedroid.entity.User;
import br.com.firedroid.repository.GameRepository;
import br.com.firedroid.repository.UserRepository;
import br.com.firedroid.security.TokenService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenService tokenService;
	
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
				.orElseThrow(() -> new IllegalArgumentException("Jogo com ID " + id + " não encontrado"));

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
				.orElseThrow(() -> new IllegalArgumentException("Jogo com ID " + id + " não encontrado"));

		return GameAdminResponse.fromEntity(game);
	}

	public void create(CreateGameRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário autenticado não encontrado"));

		Game game = new Game();
		game.setName(request.name());
		game.setDescription(request.description());
		game.setImage(request.imageUrl());
		game.setSteamLink(request.steamLink());
		game.setEpicLink(request.epicLink());
		game.setItchioLink(request.itchioLink());
		game.setSiteLink(request.siteLink());
		game.setCreatedBy(user);

		gameRepository.save(game);

	}

	public void update(Long id, CreateGameRequest request) {
		Game game = gameRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Jogo com ID " + id + " não encontrado"));

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário autenticado não encontrado"));

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

	public void delete(Long id) {
		Game game = gameRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Jogo com ID " + id + " não encontrado"));

		gameRepository.delete(game);
	}
}