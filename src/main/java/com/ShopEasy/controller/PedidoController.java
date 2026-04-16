package com.ShopEasy.controller;

import com.ShopEasy.dto.PedidoRequestDto;
import com.ShopEasy.dto.PedidoResponseDto;
import com.ShopEasy.entity.Pedido;
import com.ShopEasy.exception.EstoqueInsuficienteException;
import com.ShopEasy.repository.PedidoRepository;
import com.ShopEasy.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public PedidoResponseDto create(@RequestBody @Valid PedidoRequestDto pedidoRequestDto) throws EstoqueInsuficienteException {
        return pedidoService.create(pedidoRequestDto);
    }
}
