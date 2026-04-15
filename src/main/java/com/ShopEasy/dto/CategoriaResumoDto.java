package com.ShopEasy.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CategoriaResumoDto(
    @Schema(description = "ID da categoria", example = "1")
    Long id,
    @Schema(description = "Nome da categoria", example = "Armazenamento")
    String nome){
}
