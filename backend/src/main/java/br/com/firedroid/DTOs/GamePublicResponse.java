package br.com.firedroid.DTOs;

import java.util.List;

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
            game.getSections().stream()
                .map(GameSectionPublicResponse::fromEntity)
                .toList()

        );
    }
}
