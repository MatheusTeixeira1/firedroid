package br.com.firedroid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.firedroid.DTOs.MessageResponse;
import br.com.firedroid.DTOs.featured_games.FeaturedGamesAdminResponse;
import br.com.firedroid.DTOs.featured_games.FeaturedGamesRequest;
import br.com.firedroid.DTOs.game.GameAdminResponse;
import br.com.firedroid.DTOs.game.GamePublicResponse;
import br.com.firedroid.service.FeaturedGamesService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/featured-games")
public class FeaturedGamesController {
	@Autowired
	private FeaturedGamesService service;

	// ----- Para usuarios -----
	@GetMapping("/")
	public ResponseEntity<List<GamePublicResponse>> getLastAdded() {
		System.out.println("------------------------");
		List<GamePublicResponse> games = service.getLastAdded();
        return ResponseEntity.ok(games); // 200 OK com lista no corpo
		
	}

	// ----- ----- ----- -----

	// ----- Para Funcionarios -----
	
	@GetMapping("/admin")
	public ResponseEntity<List<FeaturedGamesAdminResponse>> getAll() {
		List<FeaturedGamesAdminResponse> featuredGames = service.getAll();
		return ResponseEntity.ok(featuredGames);
	}
	@GetMapping("/{id}/admin")
	public ResponseEntity<FeaturedGamesAdminResponse> getById(@PathVariable Long id) {
		FeaturedGamesAdminResponse featuredGame = service.getById(id);
		return ResponseEntity.ok(featuredGame);
	}
	@GetMapping("/last-added/admin")
	public ResponseEntity<List<GameAdminResponse>> getLastAddedAdmin() {

		List<GameAdminResponse> games = service.getLastAddedAdmin();
        return ResponseEntity.ok(games); // 200 OK com lista no corpo

	}
	
	
	@PostMapping
	public ResponseEntity<MessageResponse> create(@Valid @RequestBody FeaturedGamesRequest request) {
		service.create(request);
		return ResponseEntity.ok(new MessageResponse("Jogos destaque criado com sucesso!"));

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.ok(new MessageResponse("Jogos destaque deletado com sucesso!"));
	}
}
