package com.ShopEasy.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CategoriaResponseDto(
    @Schema(description = "ID da categoria", example = "1")
    Long id,
    @Schema(description = "Nome da categoria", example = "Armazenamento")
    String nome,
    @Schema(description = "Descrição da categoria", example = "HDs, SSDs e dispositivos de armazenamento de dados")
    String descricao,
    @Schema(description = "Lista de produtos cadastrados com esta categoria")
    List<ProdutoResumoDto> produtos){
}
