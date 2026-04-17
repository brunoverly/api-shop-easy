package com.ShopEasy.controller;

import com.ShopEasy.dto.CategoriaResponseDto;
import com.ShopEasy.dto.ProdutoRequestDto;
import com.ShopEasy.dto.ProdutoResponseDto;
import com.ShopEasy.exception.ExceptionModel;
import com.ShopEasy.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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


    @Operation(summary = "Listar produtos", description = "Lista todos os produtos cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = ProdutoResponseDto.class))
                    )),
            @ApiResponse(responseCode = "500",
                    description = "Erro ao conectar ao banco")
    })
    @GetMapping
    public Page<ProdutoResponseDto> findAll(@PageableDefault(size = 10, sort = "nome") Pageable pageable,
                                            @RequestParam(required = false) String nome,
                                            @RequestParam(required = false) Long idCategoria) {

        return produtoService.findAll(pageable, nome, idCategoria);
    }

    @Operation(summary = "Buscar produto por ID", description = "Retorna o produto específico do ID informado")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = ProdutoResponseDto.class)
                    )),
            @ApiResponse(responseCode = "404",
                    description = "ID não localizado",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionModel.class)
                    ))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> findById(@PathVariable Long id) {
        return produtoService.findById(id);
    }


    @Operation(summary = "Desativar um produto", description = "Desativa um produto cadastrado")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Desativado com sucesso"),
            @ApiResponse(responseCode = "404",
                    description = "ID não localizado",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionModel.class)
                    ))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        return produtoService.delete(id);
    }

    @Operation(summary = "Atualizar um produto", description = "Atualiza um produto especifico no sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionModel.class)
                    )),
            @ApiResponse(responseCode = "404",
                    description = "ID não localizado",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionModel.class)
                    ))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> updateProduto(@PathVariable Long id, @RequestBody @Valid ProdutoRequestDto produtoRequestDto) {
        return produtoService.update(id, produtoRequestDto);
    }


}
