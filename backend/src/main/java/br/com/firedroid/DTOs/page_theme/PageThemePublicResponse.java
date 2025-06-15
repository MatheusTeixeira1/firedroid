package br.com.firedroid.DTOs.page_theme;

import java.io.Serializable;
import br.com.firedroid.entity.PageTheme;

public record PageThemePublicResponse(
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
    Integer paragraphSize
) implements Serializable {
    public static PageThemePublicResponse fromEntity(PageTheme pageTheme) {
        return new PageThemePublicResponse(
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
            pageTheme.getParagraphSize()
        );
    }
}