package com.keilacouto.provanivel2.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.keilacouto.provanivel2.enums.EnumStatusPedido;
import com.keilacouto.provanivel2.enums.EnumStatusProduto;
import com.keilacouto.provanivel2.enums.EnumTipoProduto;
import com.keilacouto.provanivel2.exceptions.ItemPedidoNotFoundException;
import com.keilacouto.provanivel2.exceptions.ItemPedidoValidaStatusException;
import com.keilacouto.provanivel2.model.ItemPedido;
import com.keilacouto.provanivel2.model.Pedido;
import com.keilacouto.provanivel2.model.Produto;
import com.keilacouto.provanivel2.repositories.ItemPedidoRepository;
import com.keilacouto.provanivel2.service.ItemPedidoService;
import com.keilacouto.provanivel2.service.PedidoService;
import com.keilacouto.provanivel2.service.ProdutoService;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService {

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ProdutoService produtoService;

	@Override
	public ItemPedido save(ItemPedido entity) {

		Pedido pedido = pedidoService.findById(entity.getPedido().getId());
		Produto produto = produtoService.findById(entity.getProduto().getId());

		validaStatusInativoProduto(produto); 

		BigDecimal valorBruto = calcularValorBruto(entity, produto);

		BigDecimal valorLiquido = calcularValorLiquido(entity, pedido, produto, valorBruto);

		entity.setValorBruto(valorBruto);
		entity.setValorLiquido(valorLiquido);
		
		ItemPedido savedItem = itemPedidoRepository.save(entity);
		
		pedido.getItensPedido().add(savedItem);
		pedidoService.update(pedido, pedido.getId());
		
		return savedItem;
	}

	@Override
	public List<ItemPedido> findAll() {
		return itemPedidoRepository.findAll();
	}

	@Override
	public Page<ItemPedido> findAll(Pageable pageable) {
		return itemPedidoRepository.findAll(pageable);
	}

	@Override
	public ItemPedido findById(UUID id) {
		Optional<ItemPedido> entityOptional = itemPedidoRepository.findById(id);

		if (entityOptional.isEmpty()) {
			throw new ItemPedidoNotFoundException("id-" + id);
		}

		return entityOptional.get();

	}

	@Override
	public ItemPedido update(ItemPedido entity, UUID id) {
		Optional<ItemPedido> entityOptional = itemPedidoRepository.findById(id);

		if (entityOptional.isEmpty()) {
			throw new ItemPedidoNotFoundException("id-" + id);
		}

		Pedido pedido = pedidoService.findById(entity.getPedido().getId());
		Produto produto = produtoService.findById(entity.getProduto().getId());

		validaStatusInativoProduto(produto);

		BigDecimal valorBruto = calcularValorBruto(entity, produto);

		BigDecimal valorLiquido = calcularValorLiquido(entity, pedido, produto, valorBruto);

		ItemPedido savedItem = entityOptional.get();
		savedItem.setValorBruto(valorBruto);
		savedItem.setValorLiquido(valorLiquido);
		savedItem.setPercentualDesconto(entity.getPercentualDesconto());
		savedItem.setQuantidade(entity.getQuantidade());

		savedItem = itemPedidoRepository.save(savedItem);
		
		pedidoService.update(pedido, pedido.getId());
		
		return savedItem;
	}

	@Override
	public void deleteById(UUID id) {
		Optional<ItemPedido> entityOptional = itemPedidoRepository.findById(id);

		if (entityOptional.isEmpty()) {
			throw new ItemPedidoNotFoundException("id-" + id);
		}

		itemPedidoRepository.deleteById(id);
	}

	private BigDecimal calcularValorBruto(ItemPedido entity, Produto produto) {
		BigDecimal valorBruto = BigDecimal.ZERO;

		valorBruto = produto.getValorUnitario().multiply(new BigDecimal(entity.getQuantidade()));

		return valorBruto;
	}

	private BigDecimal calcularValorLiquido(ItemPedido entity, Pedido pedido, Produto produto, BigDecimal valorBruto) {

		BigDecimal valorLiquido = BigDecimal.ZERO;

		if (pedido.getStatus().getDescricao().equals(EnumStatusPedido.ABERTO.getDescricao())
				&& produto.getTipo().getDescricao().equals(EnumTipoProduto.PRODUTO.getDescricao())) {

			BigDecimal percentualDesconto = entity.getPercentualDesconto();

			valorLiquido = valorBruto.subtract(valorBruto.multiply(percentualDesconto).divide(new BigDecimal(100))) ;
		} else {
			valorLiquido = valorBruto;
		}

		return valorLiquido;
	}

	private void validaStatusInativoProduto(Produto produto) {
		if (produto.getStatus().getDescricao().equals(EnumStatusProduto.INATIVO.getDescricao())) {
			throw new ItemPedidoValidaStatusException();
		}
	}

}
