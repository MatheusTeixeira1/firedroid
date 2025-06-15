package br.com.firedroid.DTOs.game;

import java.time.LocalDateTime;
import java.util.List;

import br.com.firedroid.DTOs.game_section.GameSectionAdminResponse;
import br.com.firedroid.DTOs.page_theme.PageThemeAdminResponse;
import br.com.firedroid.entity.Game;

public record GameAdminResponse(
    Long id,
    String name,
    String description,
    String image,
    String steamLink,
    String epicLink,
    String itchioLink,
    String siteLink,
    PageThemeAdminResponse pageTheme,
    List<GameSectionAdminResponse> sections,
    String createdBy,
    String updatedBy,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
	public static GameAdminResponse fromEntity(Game game) {
        return new GameAdminResponse(
            game.getId(),
            game.getName(),
            game.getDescription(),
            game.getImage(),
            game.getSteamLink(),
            game.getEpicLink(),
            game.getItchioLink(),
            game.getSiteLink(),
            PageThemeAdminResponse.fromEntity(game.getPageTheme()),
            game.getSections().stream()
                .map(GameSectionAdminResponse::fromEntity)
                .toList(),
            game.getCreatedBy().getUsername(),
            game.getUpdatedBy() != null ? game.getUpdatedBy().getUsername() : null,
            game.getCreatedAt(),
            game.getUpdatedAt()
        );
    }
}
