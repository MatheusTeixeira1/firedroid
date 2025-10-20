package br.com.firedroid.DTOs.game_distributors;

import br.com.firedroid.entity.GameDistributors;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GameDistributorPublicResponse(
        @NotNull(message = "ID cannot be null") Long id,
        @NotBlank(message = "Name cannot be blank") String name,
        String link,
        @NotBlank(message = "Image cannot be blank") String image,
        @NotNull(message = "Display order cannot be null") Integer displayOrder
) {
    public static GameDistributorPublicResponse fromEntity(GameDistributors distributor) {
        return new GameDistributorPublicResponse(
                distributor.getId(),
                distributor.getName(),
                distributor.getLink(),
                distributor.getImage(),
                distributor.getDisplayOrder()
        );
    }
}
