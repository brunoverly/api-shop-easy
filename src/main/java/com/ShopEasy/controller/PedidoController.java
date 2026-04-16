package com.ShopEasy.controller;

import com.ShopEasy.dto.PedidoRequestDto;
import com.ShopEasy.dto.PedidoResponseDto;
import com.ShopEasy.entity.Pedido;
import com.ShopEasy.exception.EstoqueInsuficienteException;
import com.ShopEasy.repository.PedidoRepository;
import com.ShopEasy.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public PedidoResponseDto create(@RequestBody @Valid PedidoRequestDto pedidoRequestDto) throws EstoqueInsuficienteException {
        return pedidoService.create(pedidoRequestDto);
    }


    @GetMapping
    public Page<PedidoResponseDto> findAll(@PageableDefault(size = 10, sort = "dataPedido") Pageable pageable) {
        return pedidoService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public PedidoResponseDto findById(@PathVariable Long id) {
        return pedidoService.findById(id);
    }
}
