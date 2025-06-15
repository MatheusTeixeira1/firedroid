package br.com.firedroid.DTOs.game_section_parallax;

import br.com.firedroid.entity.GameSectionParallaxLayer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GameSectionParallaxLayerPublicResponse(
        @NotNull(message = "ID cannot be null") Long id,
        @NotBlank(message = "Image cannot be blank") String image,
        @NotNull(message = "Speed cannot be null") Integer speed
) {
	public static GameSectionParallaxLayerPublicResponse fromEntity(GameSectionParallaxLayer parallaxLayer) {
        return new GameSectionParallaxLayerPublicResponse(
        		parallaxLayer.getId(),
        		parallaxLayer.getImage(),
        		parallaxLayer.getSpeed()


        );
    }
}
