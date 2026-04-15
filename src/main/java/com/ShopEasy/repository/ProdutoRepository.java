package com.ShopEasy.repository;

import com.ShopEasy.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query(
            "SELECT p FROM Produto p WHERE p.ativo = true"
    )
    Page<Produto> findAllByAtivo(Pageable pageable);


    @Query(
            "SELECT p FROM Produto p WHERE p.ativo = true AND p.id = :id"
    )
    Produto findByIdByAtivo(Long id);
}
