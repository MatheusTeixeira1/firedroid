package br.com.firedroid.DTOs.game_distributors;

import java.time.LocalDateTime;
import br.com.firedroid.entity.GameDistributors;

public record GameDistributorAdminResponse(
        Long id,
        String name,
        String link,
        String image,
        Integer displayOrder,
        String createdByUsername,
        String updatedByUsername,
        LocalDateTime updatedAt,
        LocalDateTime createdAt
) {
    public static GameDistributorAdminResponse fromEntity(GameDistributors entity) {
        return new GameDistributorAdminResponse(
                entity.getId(),
                entity.getName(),
                entity.getLink(),
                entity.getImage(),
                entity.getDisplayOrder(),
                entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null,
                entity.getUpdatedBy() != null ? entity.getUpdatedBy().getUsername() : null,
                entity.getUpdatedAt(),
                entity.getCreatedAt()
        );
    }
}
