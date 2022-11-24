package com.keilacouto.provanivel2.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.keilacouto.provanivel2.model.Produto;

public interface ProdutoService {

	Produto save(Produto entity);
 
    List<Produto> findAll();
 
    Produto update(Produto entity, UUID id);
 
    void deleteById(UUID id);

	Produto findById(UUID id);

	Page<Produto> findAll(Pageable pageable);
}
