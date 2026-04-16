package com.ShopEasy.service;

import com.ShopEasy.config.ProdutoSpecification;
import com.ShopEasy.dto.ProdutoRequestDto;
import com.ShopEasy.dto.ProdutoResponseDto;
import com.ShopEasy.entity.Categoria;
import com.ShopEasy.entity.Produto;
import com.ShopEasy.mapper.EntityToDtoMapper;
import com.ShopEasy.repository.CategoriaRepositoy;
import com.ShopEasy.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<ProdutoResponseDto> findAll(Pageable pageable, String nome, Long idCategoria) {
        log.info("entrou no método de listar todos os produtos ativos");

        Specification<Produto> spec = ProdutoSpecification.temNome(nome)
                .and(ProdutoSpecification.temCategoria(idCategoria))
                .and(ProdutoSpecification.ativo());

        Page<Produto> produtos =  produtoRepository.findAll(spec, pageable);
        Page<ProdutoResponseDto> produtosResponseDto = produtos.map(mapper::entityToDto);

        log.info("encerrou o método de listar todos os produtos com sucesso");
        return produtosResponseDto;
    }

    public ResponseEntity<ProdutoResponseDto> findById(Long id) {
        log.info("entrou no método de buscar um produto por id");
        Produto produto = checkarSeProdutoExisteAtivo(id);
        log.info("método de buscar por id finalizado com sucesso");
        return ResponseEntity.ok(mapper.entityToDto(produto));
    }

    public ResponseEntity delete(Long id) {
        log.info("entrou no método de desativar o produto por id");
        Produto produto = checkarSeProdutoExisteAtivo(id);

        produto.setAtivo(false);
        produtoRepository.save(produto);

        log.info("método finalizado com sucesso");
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<ProdutoResponseDto> update(Long id, @Valid ProdutoRequestDto produtoRequestDto) {
        log.info("entrou no método de atualizar o produto");

        Produto produto = checkarSeProdutoExisteAtivo(id);
        Categoria categoria = categoriaRepositoy.findById(produtoRequestDto.idCategoria())
                        .orElseThrow(()-> new EntityNotFoundException("Não foi localizada uma categoria com o id informado no banco"));

        log.info("recriando o produto e atualizando os novos dados");
        produto.setNome(produtoRequestDto.nome());
        produto.setDescricao(produtoRequestDto.descricao());
        produto.setPreco(produtoRequestDto.preco());
        produto.setQtdEstoque(produtoRequestDto.qtdEstoque());
        produto.setCategoria(categoria);
        produtoRepository.save(produto);

        log.info("método de atualizar produto encerrado com sucesso");
        return ResponseEntity.ok(mapper.entityToDto(produto));

    }








    private Produto checkarSeProdutoExisteAtivo(Long id) {
        Produto produto = produtoRepository.findByIdByAtivo(id);
        if(produto == null) {
            throw new EntityNotFoundException("Não foi localizado nenhum produto ativo com o ID informado");
        }
        return produto;
    }
}
