package com.ShopEasy.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public record ItemPedidoResumoDto(
        Long id,
        int quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal,
        String numeroPedido,
        Long idProduto) {
}
