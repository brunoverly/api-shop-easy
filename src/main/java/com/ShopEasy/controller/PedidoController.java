package com.ShopEasy.controller;

import com.ShopEasy.dto.CategoriaResponseDto;
import com.ShopEasy.dto.PedidoRequestDto;
import com.ShopEasy.dto.PedidoResponseDto;
import com.ShopEasy.exception.EstoqueInsuficienteException;
import com.ShopEasy.exception.ExceptionModel;
import com.ShopEasy.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;



    @Operation(summary = "Cadastrar pedido", description = "Cadastra um novo pedido no sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    content = @Content(
                            schema = @Schema(implementation = PedidoResponseDto.class)
                    )),
            @ApiResponse(responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionModel.class)
                    )),
            @ApiResponse(responseCode = "422",
                    description = "Produto com estoque insuficiente",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionModel.class)
                    )),
    })
    @PostMapping
    public PedidoResponseDto create(@RequestBody @Valid PedidoRequestDto pedidoRequestDto) throws EstoqueInsuficienteException {
        return pedidoService.create(pedidoRequestDto);
    }

    @Operation(summary = "Listar Pedidos", description = "Lista todos os pedidos no sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = CategoriaResponseDto.class))
                    )),
    })
    @GetMapping
    public Page<PedidoResponseDto> findAll(@PageableDefault(size = 10, sort = "dataPedido") Pageable pageable) {
        return pedidoService.findAll(pageable);
    }

    @Operation(summary = "Buscar pedido por ID", description = "Busca um pedido pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = PedidoResponseDto.class)
                    )),
            @ApiResponse(responseCode = "404",
                    description = "Id não localizado no banco",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionModel.class)
                    )),
    })
    @GetMapping("/{id}")
    public PedidoResponseDto findById(@PathVariable Long id) {
        return pedidoService.findById(id);
    }



    @Operation(summary = "Cancelar um pedido", description = "Cancela um pedido pelo seu Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204"
                    ),
            @ApiResponse(responseCode = "400",
                    description = "Pedido já consta cancelado",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionModel.class)
                    )),
            @ApiResponse(responseCode = "404",
                    description = "Pedido não localizado no banco",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionModel.class)
                    )),
    })
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity cancelarPedido(@PathVariable Long id) {
        return pedidoService.cancelarPedido(id);
    }
}
