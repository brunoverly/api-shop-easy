package com.ShopEasy.repository;

import com.ShopEasy.entity.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.CancellationException;

@Repository
public interface CategoriaRepositoy extends JpaRepository<Categoria, Long> {

    Page<Categoria> findAll(Pageable pageable);
}
