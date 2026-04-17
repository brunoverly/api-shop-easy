package com.ShopEasy.service;

import com.ShopEasy.dto.CategoriaRequestDto;
import com.ShopEasy.dto.CategoriaResponseDto;
import com.ShopEasy.entity.Categoria;
import com.ShopEasy.mapper.EntityToDtoMapper;
import com.ShopEasy.repository.CategoriaRepositoy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
public class CategoriaService {

    @Autowired
    private CategoriaRepositoy categoriaRepositoy;

    @Autowired
    private EntityToDtoMapper mapper;
    @Autowired
    private ResourcePatternResolver resourcePatternResolver;


    public ResponseEntity<CategoriaResponseDto> save(CategoriaRequestDto categoriaRequestDto){
        log.info("entrando no método para criar uma categoria");
        Categoria categoria = Categoria.builder()
                .nome(categoriaRequestDto.nome())
                .descricao(categoriaRequestDto.descricao())
                .build();

        log.info("salvando a nova categoria: {}", categoria);
        categoriaRepositoy.save(categoria);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoria.getId())
                .toUri();

        log.info("método de salvar encerrado com sucesso: {}", uri);
        return ResponseEntity.created(uri).body(mapper.entityToDto(categoria));
    }

    public Page<CategoriaResponseDto> findAll(Pageable pageable) {
        log.info("entrando no método de listar todas as categorias");
        Page<Categoria> categorias =  categoriaRepositoy.findAll(pageable);
        Page<CategoriaResponseDto> categoriasDto = categorias.map(mapper::entityToDto);
        log.info("método de listar todas categorias encerrado com sucesso");
        return categoriasDto;
    }


}
