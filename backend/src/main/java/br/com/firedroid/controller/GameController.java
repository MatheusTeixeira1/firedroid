package br.com.firedroid.controller;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.firedroid.DTOs.CreateGameRequest;
import br.com.firedroid.DTOs.GameAdminResponse;
import br.com.firedroid.DTOs.GamePublicResponse;
import br.com.firedroid.service.GameService;

@RestController
@RequestMapping("/api/game")
public class GameController {

	@Autowired
	private GameService gameService;

	// ----- Para usuarios -----
	@GetMapping
	public ResponseEntity<List<GamePublicResponse>> getAll() {
		List<GamePublicResponse> games = gameService.getAll();
		return ResponseEntity.ok(games);
	}

	@GetMapping("/{id}")
	public ResponseEntity<GamePublicResponse> getById(@PathVariable Long id) {
		GamePublicResponse game = gameService.getById(id);
		return ResponseEntity.ok(game);
	}
	// ----- ----- ----- -----

	// ----- Para Funcionarios -----

	@GetMapping("/adm")
	public ResponseEntity<List<GameAdminResponse>> getAllAdmin() {
		List<GameAdminResponse> games = gameService.getAllAdmin();
		return ResponseEntity.ok(games);
	}

	@GetMapping("/{id}/adm")
	public ResponseEntity<GameAdminResponse> getByIdAdmin(@PathVariable Long id) {
		GameAdminResponse game = gameService.getByIdAdmin(id);
		return ResponseEntity.ok(game);
	}

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody CreateGameRequest request) {
		gameService.create(request);
		return ResponseEntity.ok().build();
		
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody CreateGameRequest request) {
		gameService.update(id, request);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		gameService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
