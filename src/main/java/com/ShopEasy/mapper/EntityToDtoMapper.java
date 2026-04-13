package com.ShopEasy.mapper;

import com.ShopEasy.dto.*;
import com.ShopEasy.entity.Categoria;
import com.ShopEasy.entity.ItemPedido;
import com.ShopEasy.entity.Pedido;
import com.ShopEasy.entity.Produto;
import jdk.jfr.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityToDtoMapper {

     ItemPedido dtoToEntity(ItemPedidoRequestDto dto);
     ItemPedidoResponseDto entityToDto(ItemPedido entity);

     Pedido dtoToEntity(PedidoRequestDto dto);
     PedidoResponseDto entityToDto(Pedido entity);

     Categoria dtoToEntity(CategoriaRequestDto dto);
     CategoriaResponseDto entityToDto(Categoria entity);


     Produto dtoToEntity(ProdutoRequestDto dto);
     ProdutoResponseDto entityToDto(Produto entity);

}
