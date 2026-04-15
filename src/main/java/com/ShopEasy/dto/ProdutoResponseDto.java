package com.ShopEasy.dto;


import com.fasterxml.jackson.annotation.JsonBackReference;

import java.math.BigDecimal;


public record ProdutoResponseDto(
     Long id,
     String nome,
     String descricao,
     BigDecimal preco,
     int qtdEstoque,
     @JsonBackReference
     CategoriaResponseDto categoria){
}
