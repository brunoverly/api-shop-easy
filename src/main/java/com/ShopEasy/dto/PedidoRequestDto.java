package com.ShopEasy.dto;

import com.ShopEasy.entity.StatusPedido;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record PedidoRequestDto(
        @NotBlank @Size(min = 1, max = 100)
        String numeroPedido,
        @NotBlank
        StatusPedido statusPedido,
        @Positive
        BigDecimal valorTotal,
        @NotBlank @Size(min = 1, max = 250)
        String nomeCliente,
        @Email
        String emailCliente,
        @NotNull
        List<ItemPedidoRequestDto> itens
){
}
