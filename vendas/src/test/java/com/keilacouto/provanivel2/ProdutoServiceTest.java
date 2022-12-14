package com.keilacouto.provanivel2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.keilacouto.provanivel2.enums.EnumStatusPedido;
import com.keilacouto.provanivel2.enums.EnumStatusProduto;
import com.keilacouto.provanivel2.enums.EnumTipoProduto;
import com.keilacouto.provanivel2.exceptions.ProdutoNotFoundException;
import com.keilacouto.provanivel2.exceptions.ProdutoValidaExclusaoException;
import com.keilacouto.provanivel2.model.ItemPedido;
import com.keilacouto.provanivel2.model.Pedido;
import com.keilacouto.provanivel2.model.Produto;
import com.keilacouto.provanivel2.repositories.ItemPedidoRepository;
import com.keilacouto.provanivel2.repositories.ProdutoRepository;
import com.keilacouto.provanivel2.service.impl.ProdutoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

	@Mock
	private ProdutoRepository produtoRepository;
	
	@Mock
	private ItemPedidoRepository itemPedidoRepository;

	@InjectMocks
	private ProdutoServiceImpl produtoService;

	@Test
	void should_save_one_produto() {
		// Preparar
		Produto produto = new Produto(null, "Produto 1", new BigDecimal(20.0), EnumTipoProduto.PRODUTO,
				EnumStatusProduto.ATIVO);
		when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

		// Executar
		Produto saved = produtoService.save(produto);

		// Verificar
		assertThat(saved).isNotNull();

		assertThat(saved.getDescricao()).isEqualTo("Produto 1");
		assertThat(saved.getValorUnitario()).isEqualTo(new BigDecimal(20.0));
		assertThat(saved.getTipo()).isEqualTo(EnumTipoProduto.PRODUTO);
		assertThat(saved.getStatus()).isEqualTo(EnumStatusProduto.ATIVO);

		verify(produtoRepository, times(1)).save(any(Produto.class));

	}
	
	@Test
	void should_update_one_produto() {
		// Preparar
		UUID idProduto = UUID.randomUUID();
		
		Produto produto = new Produto(idProduto, "Produto 1", new BigDecimal(20.0), EnumTipoProduto.PRODUTO,
				EnumStatusProduto.ATIVO);
		when(produtoRepository.findById(idProduto)).thenReturn(Optional.of(produto));
		when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

		// Executar
		Produto saved = produtoService.update(produto, produto.getId());

		// Verificar
		assertThat(saved).isNotNull();

		assertThat(saved.getDescricao()).isEqualTo("Produto 1");
		assertThat(saved.getValorUnitario()).isEqualTo(new BigDecimal(20.0));
		assertThat(saved.getTipo()).isEqualTo(EnumTipoProduto.PRODUTO);
		assertThat(saved.getStatus()).isEqualTo(EnumStatusProduto.ATIVO);

		verify(produtoRepository, times(1)).save(any(Produto.class));

	}

	@Test
	void should_not_found_a_produto_that_doesnt_exists() {
		// Preparar
		when(produtoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

		// Executa e verifica
		Assertions.assertThrows(ProdutoNotFoundException.class, () -> {
			produtoService.findById(UUID.randomUUID());
		});

		verify(produtoRepository, times(1)).findById(any(UUID.class));

	}

	@Test
	void should_found_a_produto_that_exists() {

		UUID id = UUID.randomUUID();
		
		Produto entity = new Produto(id, "Produto 1", new BigDecimal(20.0), EnumTipoProduto.PRODUTO,
				EnumStatusProduto.ATIVO);
		
		// Preparar
		when(produtoRepository.findById(id)).thenReturn(Optional.of(entity));

		// Executa e verifica
		Produto actual = produtoService.findById(id);

		assertThat(actual).isNotNull();

		assertThat(actual.getDescricao()).isEqualTo("Produto 1");
		assertThat(actual.getValorUnitario()).isEqualTo(new BigDecimal(20.0));
		assertThat(actual.getTipo()).isEqualTo(EnumTipoProduto.PRODUTO);
		assertThat(actual.getStatus()).isEqualTo(EnumStatusProduto.ATIVO);

		verify(produtoRepository, times(1)).findById(id);

	}
	
	@Test
    void should_not_update_when_produto_doesnt_exists() {
        // Preparar
        when(produtoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Executa e verifica
        Assertions.assertThrows(ProdutoNotFoundException.class, () -> {        	
        	produtoService.update(any(Produto.class), UUID.randomUUID());        	
        });
        
        verify(produtoRepository, times(1)).findById(any(UUID.class));
        
    }
	
	@Test
    void should_not_delete_when_produto_doesnt_exists() {
        // Preparar
        when(produtoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Executa e verifica
        Assertions.assertThrows(ProdutoNotFoundException.class, () -> {        	
        	produtoService.deleteById(UUID.randomUUID());        	
        });
        
        verify(produtoRepository, times(1)).findById(any(UUID.class));
        
    }
	
	@Test
    void should_not_delete_one_produto_when_produto_associado_itemPedido() {
        // Preparar		
		Date dataPedido = new Date();
		UUID idProduto = UUID.randomUUID();
		UUID idPedido = UUID.randomUUID();
		UUID idItemPedido = UUID.randomUUID();
		
		Produto produto = new Produto(idProduto, "Produto 1", new BigDecimal(20.0), EnumTipoProduto.PRODUTO,
				EnumStatusProduto.ATIVO);
        when(produtoRepository.findById(idProduto)).thenReturn(Optional.of(produto));
        
        Pedido pedido = new Pedido(idPedido, dataPedido, new BigDecimal(190.0), 
				new BigDecimal(171.0), EnumStatusPedido.ABERTO, new ArrayList<ItemPedido>());		
        
        ItemPedido itemPedido = new ItemPedido(idItemPedido, pedido, produto, 2, new BigDecimal(10), 
        		new BigDecimal(190.0), new BigDecimal(171.0));
		
        pedido.getItensPedido().add(itemPedido);
		
        when(itemPedidoRepository.findByProduto(produto)).thenReturn(pedido.getItensPedido());
     
        // Executa e verifica
        Assertions.assertThrows(ProdutoValidaExclusaoException.class, () -> {        	
        	produtoService.deleteById(produto.getId());        	
        });
        
        // Verificar
        verify(produtoRepository, times(1)).findById(idProduto);
        verify(itemPedidoRepository, times(1)).findByProduto(produto);
	}
	
	@Test
    void should_delete_one_produto() {
        // Preparar		
		UUID id = UUID.randomUUID();
		
		Produto produto = new Produto(id, "Produto 1", new BigDecimal(20.0), EnumTipoProduto.PRODUTO,
				EnumStatusProduto.ATIVO);
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));
        
        when(itemPedidoRepository.findByProduto(produto)).thenReturn(null);
     
        // Executar
        produtoService.deleteById(produto.getId());
        
        // Verificar
        verify(produtoRepository, times(1)).deleteById(produto.getId());
	}
}
