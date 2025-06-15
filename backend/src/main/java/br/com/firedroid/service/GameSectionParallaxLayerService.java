package br.com.firedroid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import br.com.firedroid.DTOs.game_section_parallax.GameSectionParallaxLayerRequest;
import br.com.firedroid.DTOs.game_section_parallax.GameSectionParallaxLayerAdminResponse;
import br.com.firedroid.DTOs.game_section_parallax.GameSectionParallaxLayerPublicResponse;

import br.com.firedroid.entity.GameSection;
import br.com.firedroid.entity.GameSectionParallaxLayer;
import br.com.firedroid.entity.User;
import br.com.firedroid.exception.CustomEntityNotFoundException;
import br.com.firedroid.exception.DuplicateDisplayOrderException;
import br.com.firedroid.exception.InvalidUsernameException;
import br.com.firedroid.repository.GameSectionParallaxLayerRepository;
import br.com.firedroid.repository.GameSectionRepository;
import br.com.firedroid.repository.UserRepository;

@Service
public class GameSectionParallaxLayerService {

	@Autowired
	private GameSectionRepository gameSectionRepository;

	@Autowired
	private UserRepository userRepository;


	
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
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Camada de id %s não encontrada", id)));
		
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
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Camada de id %s não encontrada",id)));
		return GameSectionParallaxLayerAdminResponse.fromEntity(layer);
	}
	
	public void create(GameSectionParallaxLayerRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new InvalidUsernameException(String.format("Usuario %s invalido.", username)));

		GameSection section = gameSectionRepository.findById(request.sectionId())
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Seção de id %s não encontrado", request.sectionId())));

		boolean displayOrderExists = parallaxLayerRepository.existsByGameSectionAndDisplayOrder(section, request.displayOrder());
		if (displayOrderExists) {
			throw new DuplicateDisplayOrderException(
	        		"Já existe uma camada com displayOrder=" + request.displayOrder());
		}

		GameSectionParallaxLayer layer = new GameSectionParallaxLayer();
		layer.setImage(request.image());
		layer.setSpeed(request.speed());
		layer.setDisplayOrder(request.displayOrder());
		layer.setGameSection(section);
		layer.setCreatedBy(user);

		parallaxLayerRepository.save(layer);
	}

	public void update(Long id, GameSectionParallaxLayerRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new InvalidUsernameException(String.format("Usuario %s invalido.", username)));


		GameSection section = gameSectionRepository.findById(request.sectionId())
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Seção de id %s não encontrado", request.sectionId())));
		
		boolean displayOrderExists = parallaxLayerRepository.existsByGameSectionAndDisplayOrder(section, request.displayOrder());
		if (displayOrderExists) {	
			throw new DuplicateDisplayOrderException(
	        		"Já existe uma camada com displayOrder=" + request.displayOrder());
		}
		GameSectionParallaxLayer layer = new GameSectionParallaxLayer();
		layer.setImage(request.image());
		layer.setSpeed(request.speed());
		layer.setDisplayOrder(request.displayOrder());
		layer.setCreatedBy(user);

		parallaxLayerRepository.save(layer);
	}


	
	public void delete(Long id) {
		GameSectionParallaxLayer layer = parallaxLayerRepository.findById(id)
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Camada de id %s não encontrada", id)));

		parallaxLayerRepository.delete(layer);
	}
}
