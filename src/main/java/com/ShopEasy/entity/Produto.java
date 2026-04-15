package com.ShopEasy.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produtos")
@Entity
@Builder
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int qtdEstoque;
    private boolean ativo;
    @ManyToOne(fetch = FetchType.LAZY)
    private Categoria categoria;

    @PrePersist
    public void onCreat() {
        this.ativo = true;
    }
}
