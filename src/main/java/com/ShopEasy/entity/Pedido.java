package com.ShopEasy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import java.math.BigDecimal;
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
    private String numeroPedido;
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
    private BigDecimal valorTotal;
    private String nomeCliente;
    private String emailCliente;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> itens;

}
