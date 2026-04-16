package com.ShopEasy.config;

import com.ShopEasy.entity.Produto;
import org.springframework.data.jpa.domain.Specification;

public class ProdutoSpecification {

    public static Specification<Produto> temNome(String nome) {
        return (root, query, cb) ->
                nome == null|| nome.isBlank() ? null :
                        cb.like(cb.lower((root.get("nome"))), "%" + nome.toLowerCase() + "%");
    }
    public static Specification<Produto> temCategoria(Long idCategoria) {
        return (root, query, cb) ->
                idCategoria == null ? null :
                        cb.equal(root.get("categoria").get("id"), idCategoria);
    }
    public static Specification<Produto> ativo() {
        return (root, query, cb) ->
                        cb.equal(root.get("ativo"), true);
    }
}
