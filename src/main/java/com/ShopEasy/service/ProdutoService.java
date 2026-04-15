package com.ShopEasy.service;

import com.ShopEasy.dto.ProdutoRequestDto;
import com.ShopEasy.dto.ProdutoResponseDto;
import com.ShopEasy.entity.Categoria;
import com.ShopEasy.entity.Produto;
import com.ShopEasy.mapper.EntityToDtoMapper;
import com.ShopEasy.repository.CategoriaRepositoy;
import com.ShopEasy.repository.ProdutoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepositoy categoriaRepositoy;

    @Autowired
    private EntityToDtoMapper mapper;

    public ResponseEntity save(ProdutoRequestDto dto) {
        log.info("entrou no método para criar um produto");

        Categoria categoria = categoriaRepositoy.findById(dto.idCategoria()).orElseThrow(() -> new EntityNotFoundException("Categoria não localizada no banco"));
        Produto produto = Produto.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .preco(dto.preco())
                .qtdEstoque(dto.qtdEstoque())
                .categoria(categoria)
                .build();

        log.info("salvando produto no banco");
        produtoRepository.save(produto);

        URI uri  = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produto.getId())
                .toUri();

        log.info("encerrou o método de criar produto com sucesso");
        return ResponseEntity.created(uri).body(mapper.entityToDto(produto));
    }

    public Page<ProdutoResponseDto> findAll(Pageable pageable) {
        log.info("entrou no método de listar todos os produtos ativos");
        Page<Produto> produtos = produtoRepository.findAllByAtivo(pageable);
        Page<ProdutoResponseDto> produtoResponseDtos = produtos.map(mapper::entityToDto);

        log.info("encerrou o método de listar todos os produtos com sucesso");
        return produtoResponseDtos;
    }

    public ResponseEntity<ProdutoResponseDto> findById(Long id) {
        Produto produto = produtoRepository.findByIdByAtivo(id);
        if(produto == null) {
            throw new EntityNotFoundException("Não foi localizado nenhum produto ativo com o ID informado");
        }
        return ResponseEntity.ok(mapper.entityToDto(produto));
    }
}
