package br.com.firedroid.DTOs.game_distributors;

import org.springframework.web.multipart.MultipartFile;

public record GameDistributorRequest(
        String name,
        String link,
        MultipartFile image,
        Integer displayOrder,
        Long gameId
) {}
