package com.ShopEasy.service;

import com.ShopEasy.dto.ItemPedidoRequestDto;
import com.ShopEasy.dto.ItemPedidoResumoDto;
import com.ShopEasy.dto.PedidoRequestDto;
import com.ShopEasy.dto.PedidoResponseDto;
import com.ShopEasy.entity.ItemPedido;
import com.ShopEasy.entity.Pedido;
import com.ShopEasy.entity.Produto;
import com.ShopEasy.entity.StatusPedido;
import com.ShopEasy.exception.EstoqueInsuficienteException;
import com.ShopEasy.exception.PedidoJaCanceladoException;
import com.ShopEasy.mapper.EntityToDtoMapper;
import com.ShopEasy.repository.PedidoRepository;
import com.ShopEasy.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;


    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EntityToDtoMapper mapper;

    @Transactional
    public PedidoResponseDto create(PedidoRequestDto pedidoRequestDto) throws EstoqueInsuficienteException {
        log.info("entrou no método para cadastrar um novo pedido");
        BigDecimal precoTotalPedido = BigDecimal.valueOf(0);
        List<ItemPedido> itens = new ArrayList<>();
        Pedido pedido = new Pedido();
        for (ItemPedidoRequestDto item : pedidoRequestDto.itens()) {
            Produto produto = produtoRepository.findByIdByAtivo(item.idProduto());
            if( produto == null) {
                throw new EntityNotFoundException("Produto com ID {" + item.idProduto() + "} não foi localizado no banco");
            }
            else if(produto.getQtdEstoque() < item.quantidade()) {
                throw new EstoqueInsuficienteException("Produto com ID {" + item.idProduto() + "} não possui estoque suficiente para este pedido");
            }

            produto.setQtdEstoque(produto.getQtdEstoque() - item.quantidade());
            produtoRepository.save(produto);

            BigDecimal valorTotalDoProduto = item.precoUnitario().multiply(BigDecimal.valueOf(item.quantidade()));
            ItemPedido itemPedido = ItemPedido.builder()
                    .quantidade(item.quantidade())
                    .pedido(pedido)
                    .precoUnitario(item.precoUnitario())
                    .subtotal(valorTotalDoProduto)
                    .produto(produto)
                    .build();
            itens.add(itemPedido);
            precoTotalPedido = precoTotalPedido.add(valorTotalDoProduto);
        }

        pedido.setNomeCliente(pedidoRequestDto.nomeCliente());
        pedido.setEmailCliente(pedidoRequestDto.emailCliente());
        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedido.setValorTotal(precoTotalPedido);
        pedido.setItens(itens);

        pedidoRepository.save(pedido);

        precoTotalPedido = BigDecimal.valueOf(0);

        List<ItemPedidoResumoDto> itensPedidoDto = new ArrayList<>();

        for(ItemPedido item : itens) {
            ItemPedidoResumoDto dto = new ItemPedidoResumoDto(
                    item.getId(),
                    item.getQuantidade(),
                    item.getPrecoUnitario(),
                    item.getSubtotal(),
                    pedido.getNumeroPedido(),
                    item.getProduto().getId()
            );

            itensPedidoDto.add(dto);
        }


        PedidoResponseDto pedidoResponseDto = new PedidoResponseDto(
                pedido.getId(),
                pedido.getNumeroPedido(),
                pedido.getDataPedido(),
                pedido.getStatus(),
                pedido.getValorTotal(),
                pedido.getNomeCliente(),
                pedido.getEmailCliente(),
                itensPedidoDto
        );

        log.info("método para criar um novo pedido encerrado com sucesso");
        return pedidoResponseDto;
    }

    public Page<PedidoResponseDto> findAll(Pageable pageable) {
        log.info("entrou no método para listar todos os pedidos");
        Page<Pedido> pedidos = pedidoRepository.findAll(pageable);
        Page<PedidoResponseDto> pedidosResponseDto = pedidos.map(mapper::entityToDto);


        log.info("método de listar todos os pedidos encerrados com sucesso");
        return pedidosResponseDto;
    }

    public PedidoResponseDto findById(Long id) {
        log.info("entrou no método de buscar pedido por id");
        Pedido pedido = pedidoRepository.findPedidosAndItensById(id).orElseThrow(
                () -> new EntityNotFoundException("Pedido com id {"+ id +"} não localizado no banco"));
        log.info("encerrou o método de buscar pedido por id");
        return mapper.entityToDto(pedido);

    }

    @Transactional
    public ResponseEntity cancelarPedido(Long id) {
        log.info("entrou no método para cancelar um pedido");
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Pedido com id {"+ id +"} não localizado no banco"));

        if(pedido.getStatus().equals(StatusPedido.CANCELADO)) {
            throw new PedidoJaCanceladoException("O pedido de id {"+ id +"} já consta como cancelado");
        }


        for(ItemPedido item : pedido.getItens()) {
            Produto produto = produtoRepository.findById(item.getProduto().getId()).get();
            produto.setQtdEstoque(produto.getQtdEstoque() + item.getQuantidade());
            produtoRepository.save(produto);
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);
        log.info("encerrou o método de cancelar pedido com sucesso");
        return ResponseEntity.noContent().build();
    }
}
