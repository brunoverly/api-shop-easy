package com.ShopEasy.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;


public record ProdutoRequestDto(
         @NotBlank(message = "Nome não pode ser nulo ou vazio")
         String nome,
         String descricao,
         @Positive(message = "Preço deve ser maior que 0")
         @Column(precision = 10, scale = 2)
         BigDecimal preco,
         @Positive(message = "Quantidade deve ser maior que 0")
         int qtdEstoque,
         @NotNull
         Long idCategoria){
}
