package br.com.firedroid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.firedroid.DTOs.MessageResponse;
import br.com.firedroid.DTOs.game_section_parallax.GameSectionParallaxLayerAdminResponse;
import br.com.firedroid.DTOs.game_section_parallax.GameSectionParallaxLayerPublicResponse;
import br.com.firedroid.DTOs.game_section_parallax.GameSectionParallaxLayerRequest;
import br.com.firedroid.service.GameSectionParallaxLayerService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/game/section/parallax-layer")
public class GameSectionParallaxLayerController {

    @Autowired
    private GameSectionParallaxLayerService service;

    // ----- Para usuários -----
    @GetMapping
    public ResponseEntity<List<GameSectionParallaxLayerPublicResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameSectionParallaxLayerPublicResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // ----- Para Funcionários -----
    @GetMapping("/adm")
    public ResponseEntity<List<GameSectionParallaxLayerAdminResponse>> getAllAdmin() {
        return ResponseEntity.ok(service.getAllAdmin());
    }

    @GetMapping("/{id}/adm")
    public ResponseEntity<GameSectionParallaxLayerAdminResponse> getByIdAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByIdAdmin(id));
    }

    @GetMapping("/getbysectionid/{sectionId}")
    public ResponseEntity<List<GameSectionParallaxLayerAdminResponse>> getBySectionId(
            @PathVariable Long sectionId) {
        return ResponseEntity.ok(service.getBySectionId(sectionId));
    }

    // ----- Criar camada -----
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<MessageResponse> create(@Valid @ModelAttribute GameSectionParallaxLayerRequest request) {
        service.create(request);
        return ResponseEntity.ok(new MessageResponse("Camada criada com sucesso!"));
    }

    // ----- Atualizar camada -----
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<MessageResponse> update(
            @PathVariable Long id,
            @Valid @ModelAttribute GameSectionParallaxLayerRequest request
    ) {
        service.update(id, request);
        return ResponseEntity.ok(new MessageResponse("Camada atualizada com sucesso!"));
    }

    // ----- Deletar camada -----
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new MessageResponse("Camada deletada com sucesso!"));
    }
}
