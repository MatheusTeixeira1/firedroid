package br.com.firedroid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.firedroid.DTOs.CreateGameRequest;
import br.com.firedroid.DTOs.CreateGameSectionRequest;
import br.com.firedroid.DTOs.GameSectionAdminResponse;
import br.com.firedroid.DTOs.GameSectionPublicResponse;
import br.com.firedroid.DTOs.UpdateGameSectionRequest;
import br.com.firedroid.entity.Game;
import br.com.firedroid.entity.GameSection;
import br.com.firedroid.entity.User;
import br.com.firedroid.repository.GameRepository;
import br.com.firedroid.repository.GameSectionRepository;
import br.com.firedroid.repository.UserRepository;
import br.com.firedroid.security.TokenService;

@Service
public class GameSectionService {

	@Autowired
	private GameSectionRepository gameSectionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private GameService gameService;

	@Autowired
	private GameRepository gameRepository;

	public GameSectionService(GameSectionRepository gameSectionRepository, UserRepository userRepository) {
		this.gameSectionRepository = gameSectionRepository;
		this.userRepository = userRepository;
	}

	// ----- Para usuarios -----

	public List<GameSectionPublicResponse> getAll() {

		List<GameSection> sections = gameSectionRepository.findAll();
		return sections.stream().map(GameSectionPublicResponse::fromEntity).toList();
	}

	public GameSectionPublicResponse getById(Long id) {
		GameSection sections = gameSectionRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Jogo com ID " + id + " não encontrado"));

		return GameSectionPublicResponse.fromEntity(sections);
	}
	// ----- ----- ----- -----

	// ----- Para Funcionarios -----

	public List<GameSectionAdminResponse> getAllAdmin() {
		List<GameSection> sections = gameSectionRepository.findAll();
		return sections.stream().map(GameSectionAdminResponse::fromEntity).toList();
	}

	public GameSectionAdminResponse getByIdAdmin(Long id) {
		GameSection sections = gameSectionRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Jogo com ID " + id + " não encontrado"));

		return GameSectionAdminResponse.fromEntity(sections);
	}

	public void create(CreateGameSectionRequest request) {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    User user = userRepository.findUserByUsername(username)
	            .orElseThrow(() -> new UsernameNotFoundException("Usuário autenticado não encontrado"));

	    Long gameId = request.gameId();
	    Game game = gameRepository.findById(gameId)
	            .orElseThrow(() -> new IllegalArgumentException("Jogo com ID " + gameId + " não encontrado"));

	    boolean displayOrderExists = gameSectionRepository.existsByGameAndDisplayOrder(game, request.displayOrder());
	    if (displayOrderExists) {
	        throw new IllegalArgumentException("Já existe uma seção com o displayOrder " + request.displayOrder() + " neste jogo.");
	    }

	    GameSection section = new GameSection();
	    section.setTitle(request.title());
	    section.setText(request.text());
	    section.setDisplayOrder(request.displayOrder());
	    section.setGame(game);
	    section.setCreatedBy(user);

	    gameSectionRepository.save(section);
	}
	
	public void update(Long id, UpdateGameSectionRequest request) {
		GameSection section = gameSectionRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Jogo com ID " + id + " não encontrado"));

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário autenticado não encontrado"));

		section.setTitle(request.title());
		section.setText(request.text());
		section.setUpdatedBy(user);

		gameSectionRepository.save(section);
	}

	public void delete(Long id) {
		GameSection section = gameSectionRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Jogo com ID " + id + " não encontrado"));

		gameSectionRepository.delete(section);
	}
}
