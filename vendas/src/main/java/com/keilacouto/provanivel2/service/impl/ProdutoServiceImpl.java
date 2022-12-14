package com.keilacouto.provanivel2.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.keilacouto.provanivel2.exceptions.ProdutoNotFoundException;
import com.keilacouto.provanivel2.exceptions.ProdutoValidaExclusaoException;
import com.keilacouto.provanivel2.model.ItemPedido;
import com.keilacouto.provanivel2.model.Produto;
import com.keilacouto.provanivel2.repositories.ItemPedidoRepository;
import com.keilacouto.provanivel2.repositories.ProdutoRepository;
import com.keilacouto.provanivel2.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Override
	public Produto save(Produto entity) {
		return produtoRepository.save(entity);
	}

	@Override
	public List<Produto> findAll() {
		return produtoRepository.findAll();
	}

	@Override
	public Page<Produto> findAll(Pageable pageable) {
		return produtoRepository.findAll(pageable);
	}

	@Override
	public Produto findById(UUID id) {
		Optional<Produto> entity = produtoRepository.findById(id);

		if (entity.isEmpty()) {
			throw new ProdutoNotFoundException("id-" + id);
		}

		return entity.get();

	}

	@Override
	public Produto update(Produto entity, UUID id) {
		Optional<Produto> entityOptional = produtoRepository.findById(id);

		if (entityOptional.isEmpty()) {
			throw new ProdutoNotFoundException("id-" + id);
		}

		entity.setId(id);

		produtoRepository.save(entity);

		return entity;
	}

	@Override
	public void deleteById(UUID id) {
		Optional<Produto> entityOptional = produtoRepository.findById(id);

		if (entityOptional.isEmpty()) {
			throw new ProdutoNotFoundException("id-" + id);
		}

		List<ItemPedido> itensPedido = itemPedidoRepository.findByProduto(entityOptional.get());

		if (itensPedido != null) {
			throw new ProdutoValidaExclusaoException();
		}

		produtoRepository.deleteById(id);
	}

}
