package com.ShopEasy.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ItemPedidoRequestDto(
        @Positive(message = "Quantidade deve ser maior que 0")
        int quantidade,
        @Positive(message = "Preço deve ser maior que 0")
        BigDecimal precoUnitario,
        @NotNull(message = "Id do produto não pode ser nulo")
        Long idProduto) {
}
