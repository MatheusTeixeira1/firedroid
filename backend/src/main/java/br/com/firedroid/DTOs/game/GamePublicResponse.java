package br.com.firedroid.DTOs.game;

import java.util.List;

import br.com.firedroid.DTOs.game_section.GameSectionPublicResponse;
import br.com.firedroid.DTOs.page_theme.PageThemePublicResponse;
import br.com.firedroid.entity.Game;

public record GamePublicResponse(
    Long id,
    String name,
    String description,
    String image,
    String steamLink,
    String epicLink,
    String itchioLink,
    String siteLink,
    PageThemePublicResponse pageTheme,
    List<GameSectionPublicResponse> sections
) {
	public static GamePublicResponse fromEntity(Game game) {
        return new GamePublicResponse(
            game.getId(),
            game.getName(),
            game.getDescription(),
            game.getImage(),
            game.getSteamLink(),
            game.getEpicLink(),
            game.getItchioLink(),
            game.getSiteLink(),
            PageThemePublicResponse.fromEntity(game.getPageTheme()),
            game.getSections().stream()
                .map(GameSectionPublicResponse::fromEntity)
                .toList()

        );
    }
}
