package br.com.firedroid.DTOs.page_theme;

import java.time.LocalDateTime;
import java.io.Serializable;

import br.com.firedroid.entity.PageTheme;

public record PageThemeAdminResponse(
    Long id,
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
    Integer paragraphSize,
    String createdBy,
    String updatedBy,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) implements Serializable {
    public static PageThemeAdminResponse fromEntity(PageTheme pageTheme) {
        return new PageThemeAdminResponse(
            pageTheme.getId(),
            pageTheme.getName(),
            pageTheme.getBackgroundColor(),
            pageTheme.getIconColor(),
            pageTheme.getTitleColor(),
            pageTheme.getParagraphColor(),
            pageTheme.getTitleWeight(),
            pageTheme.getParagraphWeight(),
            pageTheme.getTitleFont(),
            pageTheme.getParagraphFont(),
            pageTheme.getTitleSize(),
            pageTheme.getParagraphSize(),
            pageTheme.getCreatedBy().getUsername(),
            pageTheme.getUpdatedBy() != null ? pageTheme.getUpdatedBy().getUsername() : null,
            pageTheme.getCreatedAt(),
            pageTheme.getUpdatedAt()
        );
    }
}