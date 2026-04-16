package com.ShopEasy.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public record PedidoRequestDto(
        @NotBlank
        String nomeCliente,
        @Email
        String emailCliente,
        @NotNull
        List<ItemPedidoRequestDto> itens
){
}
