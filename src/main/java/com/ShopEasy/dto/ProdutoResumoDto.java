package com.ShopEasy.dto;


import java.math.BigDecimal;


public record ProdutoResumoDto(
     Long id,
     String nome,
     String descricao,
     BigDecimal preco,
     int qtdEstoque){
}
