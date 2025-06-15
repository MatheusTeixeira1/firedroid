package br.com.firedroid.DTOs.game_section;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GameSectionRequest(
	    @NotBlank(message = "O título é obrigatório")
	    String title,

	    @NotBlank(message = "O texto é obrigatório")
	    String text,

	    @NotNull(message = "A ordem de exibição é obrigatória")
	    Integer displayOrder,

	    @NotNull(message = "O ID do jogo é obrigatório")
	    Long gameId
	) {}
