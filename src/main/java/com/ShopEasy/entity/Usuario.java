package com.ShopEasy.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String telefone;
    private LocalDate dataCadastro;
    private boolean ativo;
    @Enumerated(EnumType.STRING)
    private Perfil peril;
    @OneToOne(fetch = FetchType.LAZY)
    private Endereco endereco;

    @PrePersist
    public void onCreat() {
        this.dataCadastro = LocalDate.now();
        this.ativo = true;
    }
}
