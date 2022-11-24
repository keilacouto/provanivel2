package com.keilacouto.provanivel2.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keilacouto.provanivel2.model.ItemPedido;
import com.keilacouto.provanivel2.model.Produto;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, UUID> {
	
	List<ItemPedido> findByProduto(Produto produto);
	
}
