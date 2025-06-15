package br.com.firedroid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.firedroid.DTOs.game_section_parallax.GameSectionParallaxLayerRequest;
import br.com.firedroid.DTOs.MessageResponse;
import br.com.firedroid.DTOs.game_section_parallax.GameSectionParallaxLayerAdminResponse;
import br.com.firedroid.DTOs.game_section_parallax.GameSectionParallaxLayerPublicResponse;
import br.com.firedroid.service.GameSectionParallaxLayerService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/game/section/parallax-layer")
public class GameSectionParallaxLayerController {
	@Autowired
	private GameSectionParallaxLayerService service;

	// ----- Para usuarios -----
	@GetMapping
	public ResponseEntity<List<GameSectionParallaxLayerPublicResponse>> getAll() {
		List<GameSectionParallaxLayerPublicResponse> layers = service.getAll();
		return ResponseEntity.ok(layers);
	}

	@GetMapping("/{id}")
	public ResponseEntity<GameSectionParallaxLayerPublicResponse> getById(@PathVariable Long id) {
		GameSectionParallaxLayerPublicResponse layers = service.getById(id);
		return ResponseEntity.ok(layers);
	}
	// ----- ----- ----- -----

	// ----- Para Funcionarios -----

	@GetMapping("/adm")
	public ResponseEntity<List<GameSectionParallaxLayerAdminResponse>> getAllAdmin() {
		List<GameSectionParallaxLayerAdminResponse> layers = service.getAllAdmin();
		return ResponseEntity.ok(layers);
	}

	@GetMapping("/{id}/adm")
	public ResponseEntity<GameSectionParallaxLayerAdminResponse> getByIdAdmin(@PathVariable Long id) {
		GameSectionParallaxLayerAdminResponse game = service.getByIdAdmin(id);
		return ResponseEntity.ok(game);
	}

	@PostMapping
	public ResponseEntity<MessageResponse> create(@Valid @RequestBody GameSectionParallaxLayerRequest request) {
		service.create(request);
		return ResponseEntity.ok(new MessageResponse("Camada criado com sucesso!"));
		
	}

	@PutMapping("/{id}")
	public ResponseEntity<MessageResponse> update(@PathVariable Long id, @Valid @RequestBody GameSectionParallaxLayerRequest request) {
		service.update(id, request);
		return ResponseEntity.ok(new MessageResponse("Camada atualizada com sucesso!"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.ok(new MessageResponse("Camada deletada com sucesso!"));
	}
}
