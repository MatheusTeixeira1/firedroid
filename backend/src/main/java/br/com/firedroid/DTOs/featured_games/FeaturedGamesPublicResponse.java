package br.com.firedroid.DTOs.featured_games;

import br.com.firedroid.DTOs.game.GamePublicResponse;
import br.com.firedroid.entity.FeaturedGames;

public record FeaturedGamesPublicResponse(
	    Long id,
	    GamePublicResponse game1,
	    GamePublicResponse game2,
	    GamePublicResponse game3,
	    GamePublicResponse game4,
	    GamePublicResponse game5
	) {
	    public static FeaturedGamesPublicResponse fromEntity(FeaturedGames entity) {
	        return new FeaturedGamesPublicResponse(
	            entity.getId(),
	            entity.getGame1() != null ? GamePublicResponse.fromEntity(entity.getGame1()) : null,
	            entity.getGame2() != null ? GamePublicResponse.fromEntity(entity.getGame2()) : null,
	            entity.getGame3() != null ? GamePublicResponse.fromEntity(entity.getGame3()) : null,
	            entity.getGame4() != null ? GamePublicResponse.fromEntity(entity.getGame4()) : null,
	            entity.getGame5() != null ? GamePublicResponse.fromEntity(entity.getGame5()) : null
	        );
	    }
	}
