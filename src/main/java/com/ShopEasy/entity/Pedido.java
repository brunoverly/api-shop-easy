package com.ShopEasy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedidos")
@Entity
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
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;

    @PrePersist
    public void prePersist() {
        this.dataPedido = LocalDateTime.now();
    }
}
