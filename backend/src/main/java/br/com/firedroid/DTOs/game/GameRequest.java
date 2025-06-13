package br.com.firedroid.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateGameRequest(
    @NotBlank(message = "O nome do jogo é obrigatório")
    @Size(min = 1, max = 100, message = "O nome deve ter entre 1 e 100 caracteres")
    String name,
    
    @Size(max = 2000, message = "A descrição deve ter no máximo 2000 caracteres")
    String description,
    
    String imageUrl,
    
    String steamLink,
    
    String epicLink,
    
    String itchioLink,
    
    String siteLink
) {
    public CreateGameRequest {
        steamLink = validateUrl(steamLink);
        epicLink = validateUrl(epicLink);
        itchioLink = validateUrl(itchioLink);
        siteLink = validateUrl(siteLink);
    }
    
    private static String validateUrl(String url) {
        if (url != null && !url.isBlank()) {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                return "https://" + url;
            }
        }
        return url;
    }
    
    
}