package br.com.firedroid.DTOs.game_section_parallax;

import java.time.LocalDateTime;

import br.com.firedroid.entity.GameSectionParallaxLayer;

public record GameSectionParallaxLayerAdminResponse(
        Long id,
        String image,
        Integer speed,
        Integer displayOrder,
        String createdByUsername,
        String updatedByUsername,
        LocalDateTime updatedAt,
        LocalDateTime createdAt
) {
    public static GameSectionParallaxLayerAdminResponse fromEntity(GameSectionParallaxLayer entity) {
        return new GameSectionParallaxLayerAdminResponse(
                entity.getId(),
                entity.getImage(),
                entity.getSpeed(),
                entity.getDisplayOrder(),
                entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null,
                entity.getUpdatedBy() != null ? entity.getUpdatedBy().getUsername() : null,
                entity.getUpdatedAt(),
                entity.getCreatedAt()
        );
    }
}
