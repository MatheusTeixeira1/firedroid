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

import br.com.firedroid.DTOs.game_section.GameSectionRequest;
import br.com.firedroid.DTOs.MessageResponse;
import br.com.firedroid.DTOs.game_section.GameSectionAdminResponse;
import br.com.firedroid.DTOs.game_section.GameSectionPublicResponse;
import br.com.firedroid.service.GameSectionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/game/section")
public class GameSectionController {

	@Autowired
	private GameSectionService gameSectionService;

	// ----- Para usuarios -----
	@GetMapping
	public ResponseEntity<List<GameSectionPublicResponse>> getAll() {
		List<GameSectionPublicResponse> games = gameSectionService.getAll();
		return ResponseEntity.ok(games);
	}

	@GetMapping("/{id}")
	public ResponseEntity<GameSectionPublicResponse> getById(@PathVariable Long id) {
		GameSectionPublicResponse game = gameSectionService.getById(id);
		return ResponseEntity.ok(game);
	}
	// ----- ----- ----- -----

	// ----- Para Funcionarios -----

	@GetMapping("/adm")
	public ResponseEntity<List<GameSectionAdminResponse>> getAllAdmin() {
		List<GameSectionAdminResponse> games = gameSectionService.getAllAdmin();
		return ResponseEntity.ok(games);
	}

	@GetMapping("/{id}/adm")
	public ResponseEntity<GameSectionAdminResponse> getByIdAdmin(@PathVariable Long id) {
		GameSectionAdminResponse game = gameSectionService.getByIdAdmin(id);
		return ResponseEntity.ok(game);
	}

	@PostMapping
	public ResponseEntity<MessageResponse> create(@Valid @RequestBody GameSectionRequest request) {
		gameSectionService.create(request);
		return ResponseEntity.ok(new MessageResponse("Seção criado com sucesso!"));
		
	}

	@PutMapping("/{id}")
	public ResponseEntity<MessageResponse> update(@PathVariable Long id, @Valid @RequestBody GameSectionRequest request) {
		gameSectionService.update(id, request);
		return ResponseEntity.ok(new MessageResponse("Seção criado com sucesso!"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
		gameSectionService.delete(id);
		return ResponseEntity.ok(new MessageResponse("Seção deletada com sucesso!"));
	}
}
