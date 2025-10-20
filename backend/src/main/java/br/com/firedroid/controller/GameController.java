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

    // ----- Para usuários -----
    @GetMapping
    public ResponseEntity<List<GamePublicResponse>> getAll() {
        return ResponseEntity.ok(gameService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GamePublicResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getById(id));
    }

    // ----- Para Funcionários -----

    @GetMapping("/adm")
    public ResponseEntity<List<GameAdminResponse>> getAllAdmin() {
        return ResponseEntity.ok(gameService.getAllAdmin());
    }

    @GetMapping("/{id}/adm")
    public ResponseEntity<GameAdminResponse> getByIdAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getByIdAdmin(id));
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<MessageResponse> create(@Valid @ModelAttribute GameRequest request) {
        gameService.create(request);
        return ResponseEntity.ok(new MessageResponse("Jogo criado com sucesso!"));
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<MessageResponse> update(
            @PathVariable Long id,
            @Valid @ModelAttribute GameRequest request
    ) {
        gameService.update(id, request);
        return ResponseEntity.ok(new MessageResponse("Jogo atualizado com sucesso!"));
    }

    @PatchMapping("/{gameId}/change-theme/{newThemeId}")
    public ResponseEntity<MessageResponse> changeTheme(@PathVariable Long gameId, @PathVariable Long newThemeId) {
        gameService.changeTheme(gameId, newThemeId);
        return ResponseEntity.ok(new MessageResponse("Tema do jogo alterado com sucesso!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        gameService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Jogo deletado com sucesso!"));
    }
}
