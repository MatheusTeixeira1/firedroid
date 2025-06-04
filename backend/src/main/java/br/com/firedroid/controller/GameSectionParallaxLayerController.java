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

import br.com.firedroid.DTOs.CreateGameSectionParallaxLayer;
import br.com.firedroid.DTOs.GameSectionParallaxLayerAdminResponse;
import br.com.firedroid.DTOs.GameSectionParallaxLayerPublicResponse;
import br.com.firedroid.service.GameSectionParallaxLayerService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/game/section/parallaxLayer")
public class GameSectionParallaxLayerController {
	@Autowired
	private GameSectionParallaxLayerService service;

	// ----- Para usuarios -----
	@GetMapping
	public ResponseEntity<List<GameSectionParallaxLayerPublicResponse>> getAll() {
		List<GameSectionParallaxLayerPublicResponse> layers = sevice.getAll();
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
	public ResponseEntity<Void> create(@Valid @RequestBody CreateGameSectionParallaxLayer request) {
		service.create(request);
		return ResponseEntity.ok().build();
		
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody GameSectionParallaxLayerController request) {
		service.update(id, request);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
