package br.com.firedroid.DTOs.game_section_parallax;

import org.springframework.web.multipart.MultipartFile;

public record GameSectionParallaxLayerRequest(
    MultipartFile image,
    Integer speed,
    Integer displayOrder,
    Long sectionId
) {}
