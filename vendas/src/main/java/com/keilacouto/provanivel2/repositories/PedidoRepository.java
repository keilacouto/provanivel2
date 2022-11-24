package com.keilacouto.provanivel2.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keilacouto.provanivel2.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
}
