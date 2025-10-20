package br.com.firedroid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import br.com.firedroid.DTOs.MessageResponse;
import br.com.firedroid.DTOs.game_distributors.GameDistributorAdminResponse;
import br.com.firedroid.DTOs.game_distributors.GameDistributorPublicResponse;
import br.com.firedroid.DTOs.game_distributors.GameDistributorRequest;
import br.com.firedroid.service.GameDistributorService;
@RestController
@RequestMapping("/api/game/distributors")
public class GameDistributorsController {

    @Autowired
    private GameDistributorService service;

    // ----- Para usuários -----
    @GetMapping
    public ResponseEntity<List<GameDistributorPublicResponse>> getAll() {
        List<GameDistributorPublicResponse> distributors = service.getAll();
        return ResponseEntity.ok(distributors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDistributorPublicResponse> getById(@PathVariable Long id) {
        GameDistributorPublicResponse distributor = service.getById(id);
        return ResponseEntity.ok(distributor);
    }

    // ----- Para funcionários -----
    @GetMapping("/adm")
    public ResponseEntity<List<GameDistributorAdminResponse>> getAllAdmin() {
        List<GameDistributorAdminResponse> distributors = service.getAllAdmin();
        return ResponseEntity.ok(distributors);
    }

    @GetMapping("/{id}/adm")
    public ResponseEntity<GameDistributorAdminResponse> getByIdAdmin(@PathVariable Long id) {
        GameDistributorAdminResponse distributor = service.getByIdAdmin(id);
        return ResponseEntity.ok(distributor);
    }
    
    @GetMapping("/distributorsbygameid/{gameId}")
    public ResponseEntity<List<GameDistributorAdminResponse>> getDistributorsByGame(
            @PathVariable Long gameId) {
        List<GameDistributorAdminResponse> distributors = service.findByGameId(gameId);
        return ResponseEntity.ok(distributors);
    }
    
    @PostMapping
    public ResponseEntity<MessageResponse> create(
            @RequestParam("name") String name,
            @RequestParam(value = "link", required = false) String link,
            @RequestParam("image") MultipartFile image,
            @RequestParam("displayOrder") Integer displayOrder,
            @RequestParam("gameId") Long gameId
    ) {
        GameDistributorRequest request = new GameDistributorRequest(name, link, image, displayOrder, gameId);
        service.create(request);
        return ResponseEntity.ok(new MessageResponse("Distribuidor criado com sucesso!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> update(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam(value = "link", required = false) String link,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("displayOrder") Integer displayOrder,
            @RequestParam("gameId") Long gameId
    ) {
        GameDistributorRequest request = new GameDistributorRequest(name, link, image, displayOrder, gameId);
        service.update(id, request);
        return ResponseEntity.ok(new MessageResponse("Distribuidor atualizado com sucesso!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new MessageResponse("Distribuidor deletado com sucesso!"));
    }
}
