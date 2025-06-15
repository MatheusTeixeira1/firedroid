package br.com.firedroid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.firedroid.DTOs.game_section.GameSectionRequest;
import br.com.firedroid.DTOs.game_section.GameSectionAdminResponse;
import br.com.firedroid.DTOs.game_section.GameSectionPublicResponse;
import br.com.firedroid.entity.Game;
import br.com.firedroid.entity.GameSection;
import br.com.firedroid.entity.User;
import br.com.firedroid.exception.CustomEntityNotFoundException;
import br.com.firedroid.exception.DuplicateDisplayOrderException;
import br.com.firedroid.exception.InvalidUsernameException;
import br.com.firedroid.repository.GameRepository;
import br.com.firedroid.repository.GameSectionRepository;
import br.com.firedroid.repository.UserRepository;

@Service
public class GameSectionService {

	@Autowired
	private GameSectionRepository gameSectionRepository;

	@Autowired
	private UserRepository userRepository;

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
		GameSection section = gameSectionRepository.findById(id)
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Seção de id %s não encontrado", id)));

		return GameSectionPublicResponse.fromEntity(section);
	}
	// ----- ----- ----- -----

	// ----- Para Funcionarios -----

	public List<GameSectionAdminResponse> getAllAdmin() {
		List<GameSection> sections = gameSectionRepository.findAll();
		return sections.stream().map(GameSectionAdminResponse::fromEntity).toList();
	}

	public GameSectionAdminResponse getByIdAdmin(Long id) {
		GameSection section = gameSectionRepository.findById(id)
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Seção de id %s não encontrado", id)));

		return GameSectionAdminResponse.fromEntity(section);
	}

	public void create(GameSectionRequest request) {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    User user = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new InvalidUsernameException(String.format("Usuario %s invalido.", username)));

	    Long gameId = request.gameId();

	    Game game = gameRepository.findById(gameId)
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Jogo de id %s não encontrado", gameId)));
	    
	    boolean displayOrderExists = gameSectionRepository.existsByGameAndDisplayOrder(game, request.displayOrder());
	    if (displayOrderExists) {
	        throw new DuplicateDisplayOrderException(
	        		"Já existe uma entidade com displayOrder=" + request.displayOrder());
	    }

	    GameSection section = new GameSection();
	    section.setTitle(request.title());
	    section.setText(request.text());
	    section.setDisplayOrder(request.displayOrder());
	    section.setGame(game);
	    section.setCreatedBy(user);

	    gameSectionRepository.save(section);
	}
	
	public void update(Long id, GameSectionRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new InvalidUsernameException(String.format("Usuario %s invalido.", username)));
		
		
		GameSection section = gameSectionRepository.findById(id)
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Seção de id %s não encontrado", id)));
		
		Game game = gameRepository.findById(id)
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Jogo de id %s não encontrado", id)));
		
		boolean displayOrderExists = gameSectionRepository.existsByGameAndDisplayOrder(game, request.displayOrder());
		if (displayOrderExists) {
			throw new DuplicateDisplayOrderException(
	        		"Já existe uma entidade com displayOrder=" + request.displayOrder());
	    }
		
		section.setTitle(request.title());
		section.setText(request.text());
		section.setDisplayOrder(request.displayOrder());
		section.setUpdatedBy(user);

		gameSectionRepository.save(section);
	}

	public void delete(Long id) {
		GameSection section = gameSectionRepository.findById(id)
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Seção de id %s não encontrado", id)));
		gameSectionRepository.delete(section);
	}
}
