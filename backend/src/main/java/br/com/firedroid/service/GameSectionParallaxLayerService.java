package br.com.firedroid.service;

import br.com.firedroid.DTOs.game_section_parallax.GameSectionParallaxLayerRequest;
import br.com.firedroid.DTOs.game_section_parallax.GameSectionParallaxLayerAdminResponse;
import br.com.firedroid.DTOs.game_section_parallax.GameSectionParallaxLayerPublicResponse;
import br.com.firedroid.entity.GameSection;
import br.com.firedroid.entity.GameSectionParallaxLayer;
import br.com.firedroid.entity.User;
import br.com.firedroid.exception.CustomEntityNotFoundException;
import br.com.firedroid.exception.DuplicateDisplayOrderException;
import br.com.firedroid.exception.InvalidUsernameException;
import br.com.firedroid.repository.GameSectionParallaxLayerRepository;
import br.com.firedroid.repository.GameSectionRepository;
import br.com.firedroid.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class GameSectionParallaxLayerService {

    private static final String UPLOAD_DIR_PARALLAX = "uploads/parallax/";

    @Autowired
    private GameSectionRepository gameSectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameSectionParallaxLayerRepository parallaxLayerRepository;

    // ----- Para usuários -----
    public List<GameSectionParallaxLayerPublicResponse> getAll() {
        return parallaxLayerRepository.findAll()
                .stream()
                .map(GameSectionParallaxLayerPublicResponse::fromEntity)
                .toList();
    }

    public GameSectionParallaxLayerPublicResponse getById(Long id) {
        GameSectionParallaxLayer layer = parallaxLayerRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(
                        String.format("Camada de id %s não encontrada", id)));
        return GameSectionParallaxLayerPublicResponse.fromEntity(layer);
    }

    public List<GameSectionParallaxLayerAdminResponse> getBySectionId(Long sectionId) {
        GameSection section = gameSectionRepository.findById(sectionId)
                .orElseThrow(() -> new CustomEntityNotFoundException(
                        String.format("Seção de id %s não encontrada", sectionId)));
        return section.getParallaxLayers()
                .stream()
                .map(GameSectionParallaxLayerAdminResponse::fromEntity)
                .toList();
    }

    // ----- Para funcionários -----
    public List<GameSectionParallaxLayerAdminResponse> getAllAdmin() {
        return parallaxLayerRepository.findAll()
                .stream()
                .map(GameSectionParallaxLayerAdminResponse::fromEntity)
                .toList();
    }

    public GameSectionParallaxLayerAdminResponse getByIdAdmin(Long id) {
        GameSectionParallaxLayer layer = parallaxLayerRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(
                        String.format("Camada de id %s não encontrada", id)));
        return GameSectionParallaxLayerAdminResponse.fromEntity(layer);
    }

    // ----- Criar camada -----
    public void create(GameSectionParallaxLayerRequest request) {
        User user = getAuthenticatedUser();

        GameSection section = gameSectionRepository.findById(request.sectionId())
                .orElseThrow(() -> new CustomEntityNotFoundException(
                        "Seção de id %s não encontrada".formatted(request.sectionId())));

        // valida duplicidade
        boolean duplicated = parallaxLayerRepository.existsByGameSectionAndDisplayOrder(section, request.displayOrder());
        if (duplicated) {
            throw new DuplicateDisplayOrderException("Já existe uma camada com displayOrder=" + request.displayOrder());
        }

        GameSectionParallaxLayer layer = new GameSectionParallaxLayer();
        layer.setSpeed(request.speed());
        layer.setDisplayOrder(request.displayOrder());
        layer.setGameSection(section);

        MultipartFile imageFile = request.image();
        if (imageFile != null && !imageFile.isEmpty()) {
            layer.setImage(saveFile(imageFile, UPLOAD_DIR_PARALLAX));
        }

        // Auditoria
        layer.setCreatedBy(user);
        layer.setCreatedAt(LocalDateTime.now());

        parallaxLayerRepository.save(layer);
    }

    // ----- Atualizar camada -----
    public void update(Long id, GameSectionParallaxLayerRequest request) {
        User user = getAuthenticatedUser();

        GameSectionParallaxLayer layer = parallaxLayerRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(
                        "Camada de id %s não encontrada".formatted(id)));

        // Seção alvo (pode ou não mudar)
        GameSection section = (request.sectionId() != null)
                ? gameSectionRepository.findById(request.sectionId())
                    .orElseThrow(() -> new CustomEntityNotFoundException(
                            "Seção de id %s não encontrada".formatted(request.sectionId())))
                : layer.getGameSection();

        // valida duplicidade ignorando a própria camada
        boolean duplicated = parallaxLayerRepository
                .existsByGameSectionAndDisplayOrderAndIdNot(section, request.displayOrder(), id);

        if (duplicated) {
            throw new DuplicateDisplayOrderException(
                    "Já existe uma camada com displayOrder=" + request.displayOrder() + " nessa seção.");
        }

        // Atualiza campos
        layer.setSpeed(request.speed());
        layer.setDisplayOrder(request.displayOrder());
        layer.setGameSection(section);

        MultipartFile imageFile = request.image();
        if (imageFile != null && !imageFile.isEmpty()) {
            layer.setImage(saveFile(imageFile, UPLOAD_DIR_PARALLAX));
        }

        // Auditoria
        layer.setUpdatedBy(user);
        layer.setUpdatedAt(LocalDateTime.now());

        parallaxLayerRepository.save(layer);
    }

    // ----- Deletar camada -----
    public void delete(Long id) {
        GameSectionParallaxLayer layer = parallaxLayerRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(
                        String.format("Camada de id %s não encontrada", id)));
        parallaxLayerRepository.delete(layer);
    }

    // ----- Utilitários -----
    private User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new InvalidUsernameException(
                        String.format("Usuário %s inválido", username)));
    }

    private String saveFile(MultipartFile file, String uploadDir) {
        try {
            Files.createDirectories(Paths.get(uploadDir));
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir, filename);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return path.toString();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo: " + file.getOriginalFilename(), e);
        }
    }
}
