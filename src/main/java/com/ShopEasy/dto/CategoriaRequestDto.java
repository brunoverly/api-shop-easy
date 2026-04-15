package com.ShopEasy.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDto(

    @NotBlank(message = "Nome da categoria não pode ser nulo ou vazio")
    @Size(max = 250)
    @Schema(description = "Nome da categoria", example = "Armazenamento")
    String nome,
    @NotBlank(message = "Descrição da categoria não pode ser nulo ou vazio")
    @Size(max = 500)
    @Schema(description = "Descrição da categoria", example = "HDs, SSDs e dispositivos de armazenamento de dados")
    String descricao){
}
