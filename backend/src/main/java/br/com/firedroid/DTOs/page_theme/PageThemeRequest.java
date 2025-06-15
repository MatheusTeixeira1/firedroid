package br.com.firedroid.DTOs.page_theme;

import jakarta.validation.constraints.NotBlank;

public record PageThemeRequest(
		@NotBlank(message = "O nome do tema é obrigatório")
		String name,
	    String backgroundColor,
	    String iconColor,
	    String titleColor,
	    String paragraphColor,
	    String titleWeight,
	    String paragraphWeight,
	    String titleFont,
	    String paragraphFont,
	    Integer titleSize,
	    Integer paragraphSize
		) {}