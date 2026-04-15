CREATE TABLE produtos(
                         id BIGSERIAL PRIMARY KEY,
                         nome VARCHAR(250),
                         descricao VARCHAR(500),
                         preco NUMERIC(10,2),
                         qtd_estoque int,
                         ativo boolean,
                         categoria_id BIGINT,
                         CONSTRAINT fk_categorias
                             FOREIGN KEY (categoria_id)
                                 REFERENCES categorias(id)

)