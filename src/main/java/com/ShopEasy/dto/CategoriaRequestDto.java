package com.ShopEasy.dto;

import com.ShopEasy.entity.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CategoriaRequestDto {
    @NotBlank
    @Size(max = 100)
    private String nome;
    @NotBlank
    @Size(max = 300)
    private String descricao;
}
