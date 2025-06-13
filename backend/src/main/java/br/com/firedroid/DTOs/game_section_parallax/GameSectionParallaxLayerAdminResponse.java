package br.com.firedroid.DTOs;

import java.time.LocalDateTime;
import java.util.Objects;

import br.com.firedroid.entity.GameSectionParallaxLayer;
import br.com.firedroid.entity.User;

public record GameSectionParallaxLayerAdminResponse(
        Long id,
        String image,
        Integer speed,
        Integer displayOrder,
        User createdBy,
        User updatedBy,
        LocalDateTime updatedAt,
        LocalDateTime createdAt) {

    public GameSectionParallaxLayerAdminResponse {
    	Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(image, "Image cannot be null");
        Objects.requireNonNull(speed, "Speed cannot be null");
        Objects.requireNonNull(displayOrder, "Dispay Order cannot be null");
        Objects.requireNonNull(createdBy, "Creator user cannot be null");
        Objects.requireNonNull(createdAt, "Creation date cannot be null");

        if (image.isBlank()) {
            throw new IllegalArgumentException("Image cannot be blank");
        }
        
        if (displayOrder < 1) {
            throw new IllegalArgumentException("Display order must be greater than 0");
        }
        
        if (updatedAt != null && createdAt != null && updatedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("Update date cannot be earlier than creation date");
        }
    }

    public static GameSectionParallaxLayerAdminResponse fromEntity(GameSectionParallaxLayer entity) {
        Objects.requireNonNull(entity, "Entity cannot be null");
        
        return new GameSectionParallaxLayerAdminResponse(
                entity.getId(),
                entity.getImage(),
                entity.getSpeed(),
                entity.getDisplayOrder(),
                entity.getCreatedBy(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getCreatedAt()
        );
    }

    public GameSectionParallaxLayerPublicResponse toPublicResponse() {
        return new GameSectionParallaxLayerPublicResponse(
                this.id(),
                this.image(),
                this.speed()
        );
    }
}