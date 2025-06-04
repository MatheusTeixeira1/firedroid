package br.com.firedroid.DTOs;

import java.time.LocalDateTime;
import java.util.List;

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
