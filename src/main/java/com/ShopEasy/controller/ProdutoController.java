package com.ShopEasy.controller;

import com.ShopEasy.dto.CategoriaResponseDto;
import com.ShopEasy.dto.ProdutoRequestDto;
import com.ShopEasy.dto.ProdutoResponseDto;
import com.ShopEasy.exception.ExceptionModel;
import com.ShopEasy.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
@Slf4j
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "Cadastrar produto", description = "Cria um novo produto no sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Produto criado com sucesso",
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
    public ResponseEntity<ProdutoResponseDto> createProduto(@RequestBody @Valid ProdutoRequestDto produtoRequestDto) {
        return produtoService.save(produtoRequestDto);
    }



    @GetMapping
    public Page<ProdutoResponseDto> findAll(@PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return produtoService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> findById(@PathVariable Long id) {
        return produtoService.findById(id);
    }


}
