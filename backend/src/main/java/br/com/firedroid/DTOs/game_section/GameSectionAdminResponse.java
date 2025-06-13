package br.com.firedroid.DTOs;

import java.time.LocalDateTime;
import java.util.List;

import br.com.firedroid.entity.GameSection;

public record GameSectionAdminResponse(
		Long id,
		String title,
		String text,
		Integer displayOrder,
		List<GameSectionParallaxLayerAdminResponse> parallaxLayers,
		String createdBy,
		String updatedBy,
		LocalDateTime updatedAt,
		LocalDateTime createdAt
		) {
	
	public static GameSectionAdminResponse fromEntity(GameSection sectionResponse) {
        return new GameSectionAdminResponse(
        		sectionResponse.getId(),
        		sectionResponse.getTitle(),
        		sectionResponse.getText(),
        		sectionResponse.getDisplayOrder(),
        		sectionResponse.getParallaxLayers().stream()
    			.map(GameSectionParallaxLayerAdminResponse::fromEntity)
    			.toList(),
                sectionResponse.getCreatedBy().getUsername(),
                sectionResponse.getUpdatedBy() != null ? sectionResponse.getUpdatedBy().getEmail() : null,
                sectionResponse.getUpdatedAt(),
                sectionResponse.getCreatedAt()
        );
    }
}
