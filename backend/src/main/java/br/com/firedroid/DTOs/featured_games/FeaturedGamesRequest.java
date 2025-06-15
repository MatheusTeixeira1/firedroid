package br.com.firedroid.DTOs.featured_games;

public record FeaturedGamesRequest (
		Long game1Id,
		Long game2Id,
		Long game3Id,
		Long game4Id,
		Long game5Id
		) {
	
}
