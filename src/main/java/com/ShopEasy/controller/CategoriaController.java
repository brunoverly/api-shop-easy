package com.ShopEasy.controller;

import com.ShopEasy.dto.CategoriaRequestDto;
import com.ShopEasy.dto.CategoriaResponseDto;
import com.ShopEasy.exception.ExceptionModel;
import com.ShopEasy.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "API para gerenciamento de categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Cadastrar categoria", description = "Cria uma nova categoria no sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Categoria criada com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = CategoriaResponseDto.class)
                    )),
            @ApiResponse(responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionModel.class)
                    ))
    })
    @PostMapping
    public ResponseEntity<CategoriaResponseDto> cadastrarCategoria(@RequestBody @Valid CategoriaRequestDto categoriaRequestDto) {
        return categoriaService.save(categoriaRequestDto);
    }

    @Operation(summary = "Listar categorias", description = "Lista todas as categorias cadastradas no sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = CategoriaResponseDto.class))
                    )),
            @ApiResponse(responseCode = "500",
                    description = "Erro ao conectar ao banco")
    })
    @GetMapping
    public Page<CategoriaResponseDto> listarCategorias(@PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return categoriaService.findAll(pageable);
    }


}
