package com.keilacouto.provanivel2.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.keilacouto.provanivel2.model.Pedido;

public interface PedidoService {

	Pedido save(Pedido entity);
 
    List<Pedido> findAll();
 
    Pedido update(Pedido entity, UUID id);
 
    void deleteById(UUID id);

	Pedido findById(UUID id);

	Page<Pedido> findAll(Pageable pageable);
}
