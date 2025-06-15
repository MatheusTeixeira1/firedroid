package br.com.firedroid.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.firedroid.DTOs.featured_games.FeaturedGamesRequest;
import br.com.firedroid.DTOs.featured_games.FeaturedGamesAdminResponse;
import br.com.firedroid.DTOs.game.GameAdminResponse;
import br.com.firedroid.DTOs.game.GamePublicResponse;
import br.com.firedroid.entity.FeaturedGames;
import br.com.firedroid.entity.Game;
import br.com.firedroid.entity.User;
import br.com.firedroid.exception.CustomEntityNotFoundException;
import br.com.firedroid.exception.InvalidUsernameException;
import br.com.firedroid.repository.FeaturedGamesRepository;
import br.com.firedroid.repository.GameRepository;
import br.com.firedroid.repository.UserRepository;

@Service
public class FeaturedGamesService {

    @Autowired
    private FeaturedGamesRepository featuredGamesRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    public FeaturedGamesService(FeaturedGamesRepository featuredGamesRepository, GameRepository gameRepository,
                                 UserRepository userRepository) {
        this.featuredGamesRepository = featuredGamesRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    // ---------- Público ----------
    public List<GamePublicResponse> getLastAdded() {
        FeaturedGames last = featuredGamesRepository
                .findTopByOrderByCreatedAtDesc()
                .orElseThrow(() -> new CustomEntityNotFoundException("Nenhum registro em destaque encontrado"));

        List<GamePublicResponse> games = new ArrayList<>();

        if (last.getGame1() != null) games.add(GamePublicResponse.fromEntity(last.getGame1()));
        if (last.getGame2() != null) games.add(GamePublicResponse.fromEntity(last.getGame2()));
        if (last.getGame3() != null) games.add(GamePublicResponse.fromEntity(last.getGame3()));
        if (last.getGame4() != null) games.add(GamePublicResponse.fromEntity(last.getGame4()));
        if (last.getGame5() != null) games.add(GamePublicResponse.fromEntity(last.getGame5()));

        return games;
    }

    // ---------- Administrativo ----------
    public List<FeaturedGamesAdminResponse> getAll() {
        List<FeaturedGames> featuredGames = featuredGamesRepository.findAll();
        return featuredGames.stream().map(FeaturedGamesAdminResponse::fromEntity).toList();
    }

    public FeaturedGamesAdminResponse getById(Long id) {
        FeaturedGames featuredGames = featuredGamesRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(
                        String.format("Destaque de jogos com ID %s não encontrado", id)));

        return FeaturedGamesAdminResponse.fromEntity(featuredGames);
    }

    public List<GameAdminResponse> getLastAddedAdmin() {
        FeaturedGames last = featuredGamesRepository
                .findTopByOrderByCreatedAtDesc()
                .orElseThrow(() -> new CustomEntityNotFoundException("Nenhum registro em destaque encontrado"));

        List<GameAdminResponse> games = new ArrayList<>();

        if (last.getGame1() != null) games.add(GameAdminResponse.fromEntity(last.getGame1()));
        if (last.getGame2() != null) games.add(GameAdminResponse.fromEntity(last.getGame2()));
        if (last.getGame3() != null) games.add(GameAdminResponse.fromEntity(last.getGame3()));
        if (last.getGame4() != null) games.add(GameAdminResponse.fromEntity(last.getGame4()));
        if (last.getGame5() != null) games.add(GameAdminResponse.fromEntity(last.getGame5()));

        return games;
    }

    public void create(FeaturedGamesRequest request) {
        validateNoDuplicateGames(request);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new InvalidUsernameException(String.format("Usuário %s inválido.", username)));

        FeaturedGames featured = new FeaturedGames();
        featured.setGame1(getGameOrNull(request.game1Id()));
        featured.setGame2(getGameOrNull(request.game2Id()));
        featured.setGame3(getGameOrNull(request.game3Id()));
        featured.setGame4(getGameOrNull(request.game4Id()));
        featured.setGame5(getGameOrNull(request.game5Id()));
        featured.setCreatedBy(user);

        featuredGamesRepository.save(featured);
    }

    public void delete(Long id) {
        FeaturedGames featuredGames = featuredGamesRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(
                        String.format("Destaque de jogos com ID %s não encontrado", id)));
        featuredGamesRepository.delete(featuredGames);
    }

    // ---------- Métodos auxiliares ----------
    public Game getGameOrNull(Long id) {
        if (id == null) return null;

        return gameRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(
                        String.format("Jogo com ID %s não encontrado", id)));
    }
    private void validateNoDuplicateGames(FeaturedGamesRequest request) {
        Set<Long> uniqueIds = new HashSet<>();

        List<Long> ids = List.of(
            request.game1Id(), request.game2Id(),
            request.game3Id(), request.game4Id(), request.game5Id()
        );

        for (Long id : ids) {
            if (id != null && !uniqueIds.add(id)) {
                throw new IllegalArgumentException("O mesmo jogo não pode ser adicionado mais de uma vez no destaque.");
            }
        }
    }

}
