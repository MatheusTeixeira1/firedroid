package br.com.firedroid.DTOs.featured_games;

import java.time.LocalDateTime;

import br.com.firedroid.DTOs.game.GameAdminResponse;
import br.com.firedroid.entity.FeaturedGames;

public record FeaturedGamesAdminResponse(
	    Long id,
	    GameAdminResponse game1,
	    GameAdminResponse game2,
	    GameAdminResponse game3,
	    GameAdminResponse game4,
	    GameAdminResponse game5,
	    String createdBy,
	    LocalDateTime updatedAt
	) {
	    public static FeaturedGamesAdminResponse fromEntity(FeaturedGames entity) {
	        return new FeaturedGamesAdminResponse(
	            entity.getId(),
	            entity.getGame1() != null ? GameAdminResponse.fromEntity(entity.getGame1()) : null,
	            entity.getGame2() != null ? GameAdminResponse.fromEntity(entity.getGame2()) : null,
	            entity.getGame3() != null ? GameAdminResponse.fromEntity(entity.getGame3()) : null,
	            entity.getGame4() != null ? GameAdminResponse.fromEntity(entity.getGame4()) : null,
	            entity.getGame5() != null ? GameAdminResponse.fromEntity(entity.getGame5()) : null,
	            entity.getCreatedAt() != null ? entity.getCreatedBy().getUsername() : null,
	            entity.getCreatedAt()
	        );
	    }
	}

