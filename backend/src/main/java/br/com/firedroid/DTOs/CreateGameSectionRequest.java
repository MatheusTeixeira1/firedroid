package br.com.firedroid.DTOs;


public record CreateGameSectionRequest(
	     String title,
	     String text,
	     Integer displayOrder,
	     Long gameId
	    
		) {

}
