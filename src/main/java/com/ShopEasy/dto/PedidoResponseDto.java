package com.ShopEasy.dto;

import com.ShopEasy.entity.ItemPedido;
import com.ShopEasy.entity.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDto(
        Long id,
        String numeroPedido,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime dataPedido,
        StatusPedido status,
        BigDecimal valorTotal,
        String nomeCliente,
        String emailCliente,
        List<ItemPedidoResumoDto> itens
){
}
