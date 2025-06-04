package br.com.firedroid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.firedroid.DTOs.CreateGameSectionParallaxLayer;
import br.com.firedroid.DTOs.CreateGameSectionRequest;
import br.com.firedroid.DTOs.GameSectionAdminResponse;
import br.com.firedroid.DTOs.GameSectionParallaxLayerAdminResponse;
import br.com.firedroid.DTOs.GameSectionParallaxLayerPublicResponse;
import br.com.firedroid.DTOs.GameSectionPublicResponse;
import br.com.firedroid.DTOs.UpdateGameSectionParallaxLayer;
import br.com.firedroid.DTOs.UpdateGameSectionRequest;
import br.com.firedroid.entity.Game;
import br.com.firedroid.entity.GameSection;
import br.com.firedroid.entity.GameSectionParallaxLayer;
import br.com.firedroid.entity.User;
import br.com.firedroid.repository.GameRepository;
import br.com.firedroid.repository.GameSectionParallaxLayerRepository;
import br.com.firedroid.repository.GameSectionRepository;
import br.com.firedroid.repository.UserRepository;
import br.com.firedroid.security.TokenService;

@Service
public class GameSectionParallaxLayerService {

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
	
	@Autowired
	private GameSectionParallaxLayerRepository parallaxLayerRepository;

	public GameSectionParallaxLayerService(GameSectionRepository gameSectionRepository, UserRepository userRepository, GameSectionParallaxLayerRepository gameSectionParallaxLayerRepository) {
		this.gameSectionRepository = gameSectionRepository;
		this.userRepository = userRepository;
		this.parallaxLayerRepository = gameSectionParallaxLayerRepository;
	}

	// ----- Para usuarios -----

	public List<GameSectionParallaxLayerPublicResponse> getAll() {

		List<GameSectionParallaxLayer> layers = parallaxLayerRepository.findAll();
		return layers.stream().map(GameSectionParallaxLayerPublicResponse::fromEntity).toList();
	}
	
	public GameSectionParallaxLayerPublicResponse getById(Long id) {
		GameSectionParallaxLayer layer = parallaxLayerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Jogo com ID " + id + " não encontrado"));

		return GameSectionParallaxLayerPublicResponse.fromEntity(layer);
	}
	
	// ----- ----- ----- -----

	// ----- Para Funcionarios -----

	public List<GameSectionParallaxLayerAdminResponse> getAllAdmin() {
		List<GameSectionParallaxLayer> layers = parallaxLayerRepository.findAll();
		return layers.stream().map(GameSectionParallaxLayerAdminResponse::fromEntity).toList();
	}
	
	public GameSectionParallaxLayerAdminResponse getByIdAdmin(Long id) {
		GameSectionParallaxLayer layer = parallaxLayerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Jogo com ID " + id + " não encontrado"));

		return GameSectionParallaxLayerAdminResponse.fromEntity(layer);
	}

	
	
	
	
	
	
	
	
	
	
	public void create(CreateGameSectionParallaxLayer request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário autenticado não encontrado"));

		Long sectionId = request.sectionId();
		GameSection section = gameSectionRepository.findById(sectionId)
				.orElseThrow(() -> new IllegalArgumentException("Seção com ID " + sectionId + " não encontrado"));

		boolean displayOrderExists = parallaxLayerRepository.existsByGameSectionAndDisplayOrder(section, request.displayOrder());
		if (displayOrderExists) {
			throw new IllegalArgumentException(
					"Já existe uma seção com o displayOrder " + request.displayOrder() + " neste jogo.");
		}

		GameSectionParallaxLayer layer = new GameSectionParallaxLayer();
		layer.setImage(request.image());
		layer.setSpeed(request.speed());
		layer.setDisplayOrder(request.displayOrder());
		layer.setGameSection(section);
		section.setCreatedBy(user);

		gameSectionRepository.save(section);
	}

	public void update(Long id, UpdateGameSectionParallaxLayer request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário autenticado não encontrado"));

		
		
		GameSectionParallaxLayer layer = new GameSectionParallaxLayer();
		layer.setImage(request.image());
		layer.setSpeed(request.speed());
		layer.setCreatedBy(user);

		parallaxLayerRepository.save(layer);
	}

	public void delete(Long id) {
		GameSectionParallaxLayer layer = parallaxLayerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Jogo com ID " + id + " não encontrado"));

		parallaxLayerRepository.delete(layer);
	}
}
