package com.keilacouto.provanivel2.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.keilacouto.provanivel2.model.ItemPedido;

public interface ItemPedidoService {

	ItemPedido save(ItemPedido entity);
 
    List<ItemPedido> findAll();
 
    ItemPedido update(ItemPedido entity, UUID id);
 
    void deleteById(UUID id);

	ItemPedido findById(UUID id);

	Page<ItemPedido> findAll(Pageable pageable);
}
