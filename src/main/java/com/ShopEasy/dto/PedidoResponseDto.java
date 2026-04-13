package com.ShopEasy.dto;

import com.ShopEasy.entity.StatusPedido;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDto(
        Long id,
        String numeroPedido,
        LocalDateTime dataPedido,
        StatusPedido statusPedido,
        BigDecimal valorTotal,
        String nomeCliente,
        String emailCliente,
        List<ItemPedidoResponseDto> itens
){
}
