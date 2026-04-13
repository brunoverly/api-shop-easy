package com.ShopEasy.dto;

import com.ShopEasy.entity.Pedido;
import com.ShopEasy.entity.Produto;
import java.math.BigDecimal;

public record ItemPedidoResponseDto(
        Long id,
        int quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal,
        PedidoResponseDto pedido,
        ProdutoResponseDto produto) {
}
