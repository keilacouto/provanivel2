package com.keilacouto.provanivel2.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.keilacouto.provanivel2.exceptions.PedidoNotFoundException;
import com.keilacouto.provanivel2.model.ItemPedido;
import com.keilacouto.provanivel2.model.Pedido;
import com.keilacouto.provanivel2.repositories.PedidoRepository;
import com.keilacouto.provanivel2.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private PedidoRepository repository;

	@Override
	public Pedido save(Pedido entity) {

		atualizaValorTotal(entity);

		return repository.save(entity);
	}

	@Override
	public List<Pedido> findAll() {
		return repository.findAll();
	}

	@Override
	public Page<Pedido> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Pedido findById(UUID id) {
		Optional<Pedido> entity = repository.findById(id);

		if (entity.isEmpty()) {
			throw new PedidoNotFoundException("id-" + id);
		}

		return entity.get();

	}

	@Override
	public Pedido update(Pedido entity, UUID id) {
		Optional<Pedido> entityOptional = repository.findById(id);

		if (entityOptional.isEmpty()) {
			throw new PedidoNotFoundException("id-" + id);
		}

		atualizaValorTotal(entity);

		entity.setId(id);

		repository.save(entity);

		return entity;
	}

	@Override
	public void deleteById(UUID id) {
		Optional<Pedido> entityOptional = repository.findById(id);

		if (entityOptional.isEmpty()) {
			throw new PedidoNotFoundException("id-" + id);
		}

		repository.deleteById(id);
	}

	private void atualizaValorTotal(Pedido entity) {

		BigDecimal valorTotalBruto = BigDecimal.ZERO;
		BigDecimal valorTotalLiquido = BigDecimal.ZERO;

		List<ItemPedido> itensPedido = entity.getItensPedido();

		if (itensPedido != null) {

			for (ItemPedido itemPedido : itensPedido) {

				BigDecimal valorBruto = itemPedido.getValorBruto();
				BigDecimal valorLiquido = itemPedido.getValorLiquido();

				valorTotalBruto = valorTotalBruto.add(valorBruto);
				valorTotalLiquido = valorTotalLiquido.add(valorLiquido);
			}
		}

		entity.setValorTotalBruto(valorTotalBruto);
		entity.setValorTotalLiquido(valorTotalLiquido);
	}

}
