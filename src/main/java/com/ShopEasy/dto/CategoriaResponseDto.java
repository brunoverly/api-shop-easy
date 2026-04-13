package com.ShopEasy.dto;

import com.ShopEasy.entity.Produto;
import jakarta.persistence.*;

import java.util.List;

public class CategoriaResponseDto {
    private Long id;
    private String nome;
    private String descricao;
    private List<ProdutoResponseDto> produto;
}
