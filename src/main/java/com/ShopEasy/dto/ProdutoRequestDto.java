package com.ShopEasy.dto;


import com.ShopEasy.entity.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;


public record ProdutoRequestDto(
         @NotBlank
         String nome,
         String descricao,
         @Positive
         BigDecimal preco,
         @Positive
         int qtdEstoque,
         @NotNull
         Long idCategoria){
}
