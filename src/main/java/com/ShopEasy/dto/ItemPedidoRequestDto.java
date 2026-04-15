package com.ShopEasy.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ItemPedidoRequestDto(
        @Positive(message = "Quantidade deve ser maior que 0")
        int quantidade,
        @Positive(message = "Preço deve ser maior que 0")
        BigDecimal precoUnitario,
        @Positive(message = "Preço total deve ser maior que 0")
        BigDecimal subtotal,
        @NotNull
        PedidoRequestDto pedido,
        @NotNull
        ProdutoRequestDto produto) {
}
