# 🛒 ShopEasy Comércio Digital - API de Pedidos

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)
![MapStruct](https://img.shields.io/badge/MapStruct-000000?style=for-the-badge&logo=java&logoColor=white)

---

## 📖 Sobre o Projeto

O **ShopEasy** é uma API robusta de e-commerce desenvolvida em Spring Boot focada no gerenciamento de produtos, categorias e um sistema seguro de pedidos. O maior desafio e foco desta API é garantir a **consistência absoluta dos dados**, assegurando que pedidos não sejam realizados sem estoque suficiente e que todas as transações financeiras e de inventário sejam seguras.

---

## ✨ Principais Pontos de Destaque

*   🛡️ **Consistência de Dados e Transações (`@Transactional`)**: O coração da API. Se qualquer item do pedido falhar (falta de estoque ou produto inativo), o banco de dados realiza o *rollback* de toda a operação.
*   💰 **Precisão Financeira**: Uso estrito de `BigDecimal` (nunca `double` ou `float`) mapeado no banco como `NUMERIC(10,2)` para garantir que cálculos monetários não sofram perda de precisão.
*   🚀 **Performance e Otimização**: Prevenção do clássico problema **N+1** utilizando `@EntityGraph` (ou `JOIN FETCH`) ao buscar pedidos, garantindo que os itens e produtos sejam carregados em uma única query otimizada. Relacionamentos estritamente configurados com `FetchType.LAZY`.
*   🛑 **Tratamento Global de Exceções**: Uso de `GlobalExceptionHandler` retornando status semânticos, como `422 Unprocessable Entity` para regras de negócio (ex: estoque insuficiente) e `400 Bad Request` para violações de estado.
*   ⚙️ **Configuração por Ambientes**: Gerenciamento de propriedades por *Spring Profiles* (`dev` e `prod`), separando a lógica de exibição de SQL e credenciais de banco de dados.

---

## 🔄 Fluxo da Informação

Abaixo está o fluxo principal da operação de negócios (O Ciclo de Vida do Pedido):

1.  **Entrada do Pedido**: O cliente envia um JSON contendo apenas o `id` dos produtos e suas respectivas quantidades.
2.  **Validação Inicial**: O servidor verifica se o preço é válido, se o produto existe e se está **ativo**.
3.  **Controle de Estoque**: A API checa o estoque atual. Se não for suficiente, a transação é abortada imediatamente (`EstoqueInsuficienteException`).
4.  **Processamento**: O estoque de cada produto é decrementado automaticamente.
5.  **Fixação de Preço**: O `precoUnitario` do item recebe o preço *atual* do produto no catálogo (congelando o valor no momento da compra).
6.  **Cálculo e Persistência**: O `subtotal` de cada item e o `valorTotal` do pedido são calculados. Um `numeroPedido` (UUID/Sequencial) é gerado e tudo é salvo no banco de forma atômica.
7.  **Cancelamento (Opcional)**: Caso o pedido seja cancelado, a API itera sobre os itens e **devolve** exatamente a mesma quantidade ao estoque dos produtos, alterando o status do pedido para `CANCELADO`.

---

## ⚖️ Regras de Negócio Implementadas

*   ✅ Produto inativo (`ativo = false`) não pode ser adicionado ao pedido.
*   ✅ Não é permitido realizar pedido sem estoque suficiente para todos os itens.
*   ✅ Preço de produto **nunca** pode ser negativo ou zero.
*   ✅ Pedido com status `CANCELADO` não pode ser cancelado novamente (`PedidoJaCanceladoException`).
*   ✅ O método de deleção de produtos é **Soft Delete** (apenas inativa, para não quebrar o histórico de pedidos).

---

## 🔌 Endpoints da API

### Produtos e Categorias
| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/categorias` | Cadastra uma nova categoria |
| `GET` | `/categorias` | Lista as categorias cadastradas |
| `POST` | `/produtos` | Cadastra um novo produto (relacionado à categoria) |
| `GET` | `/produtos` | Lista produtos (com paginação e filtros) |
| `GET` | `/produtos/{id}`| Busca detalhes de um produto específico |
| `PUT` | `/produtos/{id}`| Atualiza os dados do produto |
| `DELETE`| `/produtos/{id}`| Realiza a exclusão lógica (Soft Delete / Inativa) |

### Pedidos
| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/pedidos` | Cria um pedido validando estoque e regras |
| `GET` | `/pedidos` | Lista os pedidos realizados (paginado) |
| `GET` | `/pedidos/{id}`| Busca um pedido específico detalhado com itens |
| `PATCH`| `/pedidos/{id}/cancelar`| Cancela pedido e **devolve estoque** aos produtos |

---

## 🚀 Como Executar e Usar

### Pré-requisitos
*   Java 17+
*   Maven
*   PostgreSQL rodando localmente (porta 5432)

### 1. Configurando o Banco de Dados
Crie o banco de dados no PostgreSQL:
```sql
CREATE DATABASE shopeasy_db;
```
2. Configurando as Variáveis de Ambiente
O projeto utiliza Spring Profiles. No arquivo application-dev.properties, configure as credenciais do seu banco local:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/shopeasy_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.show-sql=true
```
3. Rodando as Migrations e Iniciando a Aplicação
O projeto utiliza o Flyway. Ao rodar o projeto pela primeira vez, as tabelas serão criadas automaticamente e populadas com os dados de teste (V5__insert_dados_teste.sql).

Execute via Maven:
```bash
mvn spring-boot:run
```
## 🧪 Fluxo de Teste Sugerido (Postman / Swagger)

Para testar a consistência do sistema de forma prática:

### 1. Criar um Produto
Faça um `POST /produtos` com:
- `estoque = 10`
- `preco = 100.00`

---

### 2. Criar Pedido de Sucesso
Faça um `POST /pedidos` solicitando **3 unidades** desse produto.

**Resultado esperado:** Pedido criado com sucesso.

---

### 3. Verificar Estoque
Faça um `GET /produtos/{id}`

**Resultado esperado:** O estoque atual deve ser **7**.

---

### 4. Tentar Fraude de Estoque
Faça um `POST /pedidos` solicitando **8 unidades**.

**Resultado esperado:**  
Erro HTTP **422** (`EstoqueInsuficienteException`)

---

### 5. Cancelar Pedido
Faça um `PATCH /pedidos/{id}/cancelar` do primeiro pedido gerado.

**Resultado esperado:** Pedido alterado para **CANCELADO**.

---

### 6. Verificar Estorno de Estoque
Faça um `GET /produtos/{id}`

**Resultado esperado:** O estoque deve ter voltado para **10**.