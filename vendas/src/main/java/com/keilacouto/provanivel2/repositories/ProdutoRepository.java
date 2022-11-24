package com.keilacouto.provanivel2.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keilacouto.provanivel2.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
}
