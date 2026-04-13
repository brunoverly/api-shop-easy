CREATE TABLE pedidos(
                         id BIGINT PRIMARY KEY,
                         numero_pedido VARCHAR(250),
                         data_pedido TIMESTAMP,
                         status VARCHAR(50),
                         valor_total NUMERIC(10,2),
                         nome_cliente VARCHAR(250),
                         email_cliente VARCHAR(250)
)