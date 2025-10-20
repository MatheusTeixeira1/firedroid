package br.com.firedroid.DTOs.game;

import java.util.List;

import br.com.firedroid.DTOs.game_distributors.GameDistributorPublicResponse;
import br.com.firedroid.DTOs.game_section.GameSectionPublicResponse;
import br.com.firedroid.DTOs.page_theme.PageThemePublicResponse;
import br.com.firedroid.entity.Game;

public record GamePublicResponse(
    Long id,
    String name,
    String description,
    String gameBanner,
    String gameIcon,
    PageThemePublicResponse pageTheme,
    List<GameDistributorPublicResponse> distributors,
    List<GameSectionPublicResponse> sections
) {
    public static GamePublicResponse fromEntity(Game game) {
        return new GamePublicResponse(
            game.getId(),
            game.getName(),
            game.getDescription(),
            game.getGameBanner(),
            game.getGameIcon(),
            PageThemePublicResponse.fromEntity(game.getPageTheme()),
            game.getDistributors().stream()
                .map(GameDistributorPublicResponse::fromEntity)
                .toList(),
            game.getSections().stream()
                .map(GameSectionPublicResponse::fromEntity)
                .toList()
        );
    }
}
