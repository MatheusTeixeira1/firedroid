package br.com.firedroid.controller;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.firedroid.DTOs.game.GameRequest;
import br.com.firedroid.DTOs.MessageResponse;
import br.com.firedroid.DTOs.game.GameAdminResponse;
import br.com.firedroid.DTOs.game.GamePublicResponse;
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
	public ResponseEntity<MessageResponse> create(@Valid @RequestBody GameRequest request) {
		gameService.create(request);
		return ResponseEntity.ok(new MessageResponse("Jogo criado com sucesso!"));
		
	}

	@PutMapping("/{id}")
	public ResponseEntity<MessageResponse> update(@PathVariable Long id, @Valid @RequestBody GameRequest request) {
		gameService.update(id, request);
		return ResponseEntity.ok(new MessageResponse("Jogo atualizado com sucesso!"));
	}

	@PatchMapping("{gameId}/chengeTheme/{newThemeId}")
	public ResponseEntity<MessageResponse> chengeTheme(@PathVariable Long gameId, @PathVariable Long newThemeId) {
		gameService.chengeTheme(gameId, newThemeId);
		return ResponseEntity.ok(new MessageResponse("Tema do jogo alterado com sucesso!"));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
		gameService.delete(id);
		return ResponseEntity.ok(new MessageResponse("Jogo deletado com sucesso!"));
	}
}
