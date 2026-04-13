package com.ShopEasy.dto;

import com.ShopEasy.entity.Pedido;
import com.ShopEasy.entity.Produto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ItemPedidoRequestDto(
        @Positive
        int quantidade,
        @Positive
        BigDecimal precoUnitario,
        @Positive
        BigDecimal subtotal,
        @NotNull
        PedidoRequestDto pedido,
        @NotNull
        ProdutoRequestDto produto) {
}
