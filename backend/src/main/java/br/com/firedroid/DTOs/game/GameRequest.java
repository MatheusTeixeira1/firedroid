package br.com.firedroid.DTOs.game;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record GameRequest(
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    String name,

    @NotBlank(message = "A descrição é obrigatória")
    String description,

    @NotBlank(message = "A imagem é obrigatória")
    @URL(message = "URL da imagem inválida")
    String imageUrl,
    
    @URL(message = "Link da Steam inválido")
    String steamLink,

    @URL(message = "Link da Epic Games inválido")
    String epicLink,

    @URL(message = "Link do Itch.io inválido")
    String itchioLink,

    @URL(message = "Link do site inválido")
    String siteLink
) {}
