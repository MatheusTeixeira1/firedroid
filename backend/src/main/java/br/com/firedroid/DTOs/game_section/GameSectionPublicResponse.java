package br.com.firedroid.DTOs.game_section;

import java.util.List;

import br.com.firedroid.DTOs.game_section_parallax.GameSectionParallaxLayerPublicResponse;
import br.com.firedroid.entity.GameSection;


public record GameSectionPublicResponse(
		Long id,
		String title,
		String text,
		List<GameSectionParallaxLayerPublicResponse> parallaxLayers
		) {
	public static GameSectionPublicResponse fromEntity(GameSection sectionResponse) {
        return new GameSectionPublicResponse(
        		sectionResponse.getId(),
        		sectionResponse.getTitle(),
        		sectionResponse.getText(),
        		sectionResponse.getParallaxLayers().stream().map(GameSectionParallaxLayerPublicResponse::fromEntity)
    			.toList()

        );
    }
}
