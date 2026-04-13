CREATE TABLE itens_pedido(
                             id BIGINT PRIMARY KEY,
                             quantidade int,
                             preco_unitario NUMERIC(10,2),
                             subtotal NUMERIC(10,2),
                             pedido_id BIGINT,
                             produto_id BIGINT,

                             CONSTRAINT fk_pedido
                                 FOREIGN KEY (pedido_id)
                                     REFERENCES pedidos(id),

                             CONSTRAINT fk_produto
                                 FOREIGN KEY (produto_id)
                                     REFERENCES produtos(id)
);