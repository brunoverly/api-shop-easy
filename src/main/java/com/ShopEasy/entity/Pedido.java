package com.ShopEasy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedidos")
@Entity
@Builder
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String numeroPedido;
    private LocalDateTime dataPedido;
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
    private BigDecimal valorTotal;
    private String nomeCliente;
    private String emailCliente;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens;

    @PrePersist
    public void prePersist() {
        this.dataPedido = LocalDateTime.now();
        this.numeroPedido = UUID.randomUUID().toString();
    }
}
