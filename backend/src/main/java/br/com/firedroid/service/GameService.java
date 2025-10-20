package br.com.firedroid.service;

import br.com.firedroid.DTOs.game.GameRequest;
import br.com.firedroid.DTOs.game.GameAdminResponse;
import br.com.firedroid.DTOs.game.GamePublicResponse;
import br.com.firedroid.entity.Game;
import br.com.firedroid.entity.PageTheme;
import br.com.firedroid.entity.User;
import br.com.firedroid.exception.CustomEntityNotFoundException;
import br.com.firedroid.exception.InvalidUsernameException;
import br.com.firedroid.repository.GameRepository;
import br.com.firedroid.repository.PageThemeRepository;
import br.com.firedroid.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class GameService {

    private static final String UPLOAD_DIR_BANNERS = "uploads/banners/";
    private static final String UPLOAD_DIR_ICONS = "uploads/icons/";

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PageThemeRepository pageThemeRepository;

    // ----- Para usuários -----

    public List<GamePublicResponse> getAll() {
        return gameRepository.findAll()
                .stream()
                .map(GamePublicResponse::fromEntity)
                .toList();
    }

    public GamePublicResponse getById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException("Jogo não encontrado"));
        return GamePublicResponse.fromEntity(game);
    }

    // ----- Para Funcionários -----

    public List<GameAdminResponse> getAllAdmin() {
        return gameRepository.findAll()
                .stream()
                .map(GameAdminResponse::fromEntity)
                .toList();
    }

    public GameAdminResponse getByIdAdmin(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException("Jogo não encontrado"));
        return GameAdminResponse.fromEntity(game);
    }

    public void create(GameRequest request) {
        // Recupera o usuário autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new CustomEntityNotFoundException(
                        String.format("Usuário %s não encontrado", username)));

        // Cria a entidade Game
        Game game = new Game();
        game.setName(request.name());
        game.setDescription(request.description());

        // Cria diretórios se não existirem
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR_BANNERS));
            Files.createDirectories(Paths.get(UPLOAD_DIR_ICONS));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar diretórios de upload", e);
        }

        // Upload do banner
        String bannerFileName = System.currentTimeMillis() + "_" + request.gameBanner().getOriginalFilename();
        Path bannerPath = Paths.get(UPLOAD_DIR_BANNERS, bannerFileName);
        try {
        	Files.copy(request.gameBanner().getInputStream(), bannerPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
        	throw new RuntimeException("Erro ao salvar banner do game", e);
        }
        game.setGameBanner(bannerPath.toString()); // salva apenas o nome do arquivo
//        if (request.gameBanner() != null && !request.gameBanner().isEmpty()) {
//        }

        // Upload do ícone
        String iconFileName = System.currentTimeMillis() + "_" + request.gameIcon().getOriginalFilename();
        Path iconPath = Paths.get(UPLOAD_DIR_ICONS, iconFileName);
        try {
        	Files.copy(request.gameIcon().getInputStream(), iconPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
        	throw new RuntimeException("Erro ao salvar ícone do game", e);
        }
        game.setGameIcon(iconPath.toString()); // salva apenas o nome do arquivo
        
//        if (request.gameIcon() != null && !request.gameIcon().isEmpty()) {
//        }

        // Define tema da página
        PageTheme theme = (request.pageThemeId() != null)
                ? pageThemeRepository.findById(request.pageThemeId())
                    .orElseThrow(() -> new CustomEntityNotFoundException(
                            String.format("Tema de id %s não foi encontrado.", request.pageThemeId())))
                : pageThemeRepository.findByName("default")
                    .orElseThrow(() -> new CustomEntityNotFoundException(
                            "O tema padrão do sistema não foi encontrado, crie um com o nome 'default'"));

        game.setPageTheme(theme);

        // Auditoria
        game.setCreatedBy(user);
        game.setCreatedAt(LocalDateTime.now());

        // Salva o game
        gameRepository.save(game);
    }


    public void update(Long id, GameRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new InvalidUsernameException(
                        String.format("Usuário %s inválido.", username)));

        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(
                        String.format("Jogo de id %s não foi encontrado.", id)));

        game.setName(request.name());
        game.setDescription(request.description());
        MultipartFile bannerFile = request.gameBanner();
        MultipartFile iconFile = request. gameIcon();
        // Upload das imagens (substitui se vier novo arquivo)
        if (bannerFile != null && !bannerFile.isEmpty()) {
            game.setGameBanner(saveFile(bannerFile, UPLOAD_DIR_BANNERS));
        }

        if (iconFile != null && !iconFile.isEmpty()) {
            game.setGameIcon(saveFile(iconFile, UPLOAD_DIR_ICONS));
        }

        // PageTheme (opcional no request)
        if (request.pageThemeId() != null) {
            PageTheme theme = pageThemeRepository.findById(request.pageThemeId())
                    .orElseThrow(() -> new CustomEntityNotFoundException(
                            String.format("Tema de id %s não foi encontrado.", request.pageThemeId())));
            game.setPageTheme(theme);
        }

        // Auditoria
        game.setUpdatedBy(user);
        game.setUpdatedAt(LocalDateTime.now());

        gameRepository.save(game);
    }

    public void changeTheme(Long gameId, Long newThemeId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new CustomEntityNotFoundException(
                        String.format("Jogo de id %s não foi encontrado.", gameId)));

        PageTheme theme = pageThemeRepository.findById(newThemeId)
                .orElseThrow(() -> new CustomEntityNotFoundException(
                        String.format("Tema de id %s não foi encontrado.", newThemeId)));

        game.setPageTheme(theme);
        gameRepository.save(game);
    }

    public void delete(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(
                        String.format("Jogo de id %s não encontrado", id)));
        gameRepository.delete(game);
    }

    // ----- Utilitários -----

    private String saveFile(MultipartFile file, String uploadDir) {
        try {
            Files.createDirectories(Paths.get(uploadDir));
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, filename);
            Files.write(filePath, file.getBytes());
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo: " + file.getOriginalFilename(), e);
        }
    }
}
