package br.com.firedroid.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.firedroid.DTOs.game_distributors.GameDistributorAdminResponse;
import br.com.firedroid.DTOs.game_distributors.GameDistributorPublicResponse;
import br.com.firedroid.DTOs.game_distributors.GameDistributorRequest;
import br.com.firedroid.entity.Game;
import br.com.firedroid.entity.GameDistributors;
import br.com.firedroid.entity.User;
import br.com.firedroid.exception.CustomEntityNotFoundException;
import br.com.firedroid.exception.DuplicateDisplayOrderException;
import br.com.firedroid.exception.InvalidUsernameException;
import br.com.firedroid.repository.Game2Repository;
import br.com.firedroid.repository.GameDistributorsRepository;
import br.com.firedroid.repository.UserRepository;

@Service
public class GameDistributorService {

    @Autowired
    private Game2Repository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameDistributorsRepository distributorsRepository;

    private final String uploadDir = "uploads/distributors";

    // ----- Para usuários -----

    public List<GameDistributorPublicResponse> getAll() {
        return distributorsRepository.findAll()
                .stream()
                .map(GameDistributorPublicResponse::fromEntity)
                .toList();
    }

    public GameDistributorPublicResponse getById(Long id) {
        GameDistributors distributor = distributorsRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(
                        String.format("Distribuidor de id %s não encontrado", id)));
        return GameDistributorPublicResponse.fromEntity(distributor);
    }

    // ----- Para funcionários -----

    public List<GameDistributorAdminResponse> getAllAdmin() {
        return distributorsRepository.findAll()
                .stream()
                .map(GameDistributorAdminResponse::fromEntity)
                .toList();
    }

    public GameDistributorAdminResponse getByIdAdmin(Long id) {
        GameDistributors distributor = distributorsRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(
                        String.format("Distribuidor de id %s não encontrado", id)));
        return GameDistributorAdminResponse.fromEntity(distributor);
    }
    public List<GameDistributorAdminResponse> findByGameId(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new CustomEntityNotFoundException("Jogo não encontrado"));
        

        return game.getDistributors().stream()
                .map(GameDistributorAdminResponse::fromEntity)
                .toList();
    }
    // ----- Criar -----

    public void create(GameDistributorRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new InvalidUsernameException("Usuário inválido: " + username));

        Game game = gameRepository.findById(request.gameId())
                .orElseThrow(() -> new CustomEntityNotFoundException("Jogo não encontrado"));

        boolean displayOrderExists = distributorsRepository.existsByGameAndDisplayOrder(game, request.displayOrder());
        if (displayOrderExists) {
            throw new DuplicateDisplayOrderException("Distribuidor com essa ordem já existe para o jogo.");
        }

        // Criar pasta se não existir
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar diretório de upload", e);
        }

        // Salvar o arquivo
        String fileName = System.currentTimeMillis() + "_" + request.image().getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        try {
            Files.copy(request.image().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar imagem", e);
        }

        GameDistributors distributor = new GameDistributors();
        distributor.setName(request.name());
        distributor.setLink(request.link());
        distributor.setImage(filePath.toString()); // salva apenas o nome do arquivo
        distributor.setDisplayOrder(request.displayOrder());
        distributor.setGame(game);
        distributor.setCreatedBy(user);

        distributorsRepository.save(distributor);
    }

    // ----- Atualizar -----

    public void update(Long id, GameDistributorRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new InvalidUsernameException("Usuário inválido: " + username));

        GameDistributors distributor = distributorsRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(
                        String.format("Distribuidor de id %s não encontrado", id)));

        Game game = gameRepository.findById(request.gameId())
                .orElseThrow(() -> new CustomEntityNotFoundException("Jogo não encontrado"));

        boolean displayOrderExists = distributorsRepository.existsByGameAndDisplayOrder(game, request.displayOrder());
        if (displayOrderExists && !distributor.getDisplayOrder().equals(request.displayOrder())) {
            throw new DuplicateDisplayOrderException("Já existe um distribuidor com esta ordem.");
        }

        if (request.image() != null && !request.image().isEmpty()) {
            try {
                Files.createDirectories(Paths.get(uploadDir));
                String fileName = System.currentTimeMillis() + "_" + request.image().getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(request.image().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                distributor.setImage(filePath.toString());
            } catch (Exception e) {
                throw new RuntimeException("Erro ao salvar imagem", e);
            }
        }

        distributor.setName(request.name());
        distributor.setLink(request.link());
        distributor.setDisplayOrder(request.displayOrder());
        distributor.setGame(game);
        distributor.setUpdatedBy(user);

        distributorsRepository.save(distributor);
    }

    // ----- Deletar -----

    public void delete(Long id) {
        GameDistributors distributor = distributorsRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(
                        String.format("Distribuidor de id %s não encontrado", id)));
        distributorsRepository.delete(distributor);
    }
}
