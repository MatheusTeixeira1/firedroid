package br.com.firedroid.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.firedroid.DTOs.page_theme.PageThemeRequest;
import br.com.firedroid.DTOs.MessageResponse;
import br.com.firedroid.DTOs.page_theme.PageThemeAdminResponse;
import br.com.firedroid.DTOs.page_theme.PageThemePublicResponse;
import br.com.firedroid.service.PageThemeService;

@RestController
@RequestMapping("/api/theme")
public class PageThemeController {

    @Autowired
    private PageThemeService pageThemeService;

    // ----- Endpoints públicos -----
    @GetMapping
    public ResponseEntity<List<PageThemePublicResponse>> getAll() {
        List<PageThemePublicResponse> themes = pageThemeService.getAll();
        return ResponseEntity.ok(themes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PageThemePublicResponse> getById(@PathVariable Long id) {
        PageThemePublicResponse theme = pageThemeService.getById(id);
        return ResponseEntity.ok(theme);
    }

    // ----- Endpoints administrativos -----
    @GetMapping("/adm")
    public ResponseEntity<List<PageThemeAdminResponse>> getAllAdmin() {
        List<PageThemeAdminResponse> themes = pageThemeService.getAllAdmin();
        return ResponseEntity.ok(themes);
    }

    @GetMapping("/{id}/adm")
    public ResponseEntity<PageThemeAdminResponse> getByIdAdmin(@PathVariable Long id) {
        PageThemeAdminResponse theme = pageThemeService.getByIdAdmin(id);
        return ResponseEntity.ok(theme);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody PageThemeRequest request) {
        pageThemeService.create(request);
        return ResponseEntity.ok(new MessageResponse("Tema criado com sucesso!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Long id, @Valid @RequestBody PageThemeRequest request) {
        pageThemeService.update(id, request);
        return ResponseEntity.ok(new MessageResponse("Tema editado com sucesso!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        pageThemeService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Tema deletado com sucesso!"));
    }
}