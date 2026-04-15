package com.ShopEasy.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ExceptionModel(
        @Schema(name = "Carimbo",
                description = "Carimbo do momento que a exception foi lançada",
                example = "2026-04-15T10:30:00")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime timestamp,

        @Schema(name = "Error",
                description = "Http erro lançado",
                example = "Not found")
        String error,

        @Schema(name = "Status",
                description = "Código do erro Http lançado",
                example = "404")
        int statusCode,

        @Schema(name = "Mensagem",
                description = "Mensagem explicativa sobre a exception lançada",
                example = "ID informado não foi localizado no banco")
        String message
) {
}
