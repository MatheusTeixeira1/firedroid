package br.com.firedroid.DTOs.game;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GameRequest(
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    String name,

    @NotBlank(message = "A descrição é obrigatória")
    String description,

    @NotNull(message = "O gameIcon é obrigatório")
    MultipartFile gameBanner,

    @NotNull(message = "O gameIcon é obrigatório")
    MultipartFile gameIcon,

    Long pageThemeId
) {}
