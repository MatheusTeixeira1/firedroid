package br.com.firedroid.DTOs.game_section_parallax;

public record GameSectionParallaxLayerRequest(
		 String image,
		 Integer speed,
	     Integer displayOrder,
	     Long sectionId
	    
		) {}