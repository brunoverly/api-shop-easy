package com.ShopEasy.dto;


import java.math.BigDecimal;


public record ProdutoResponseDto(
     Long id,
     String nome,
     String descricao,
     BigDecimal preco,
     int qtdEstoque,
     CategoriaResponseDto categoria){
}
