package com.ShopEasy.repository;

import com.ShopEasy.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query(
            "SELECT p FROM Pedido p LEFT JOIN FETCH p.itens WHERE p.id = :id"
    )
    Optional<Pedido> findPedidosAndItensById(Long id);
}
