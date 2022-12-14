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
import com.keilacouto.provanivel2.exceptions.ItemPedidoNotFoundException;
import com.keilacouto.provanivel2.exceptions.ItemPedidoValidaStatusException;
import com.keilacouto.provanivel2.model.ItemPedido;
import com.keilacouto.provanivel2.model.Pedido;
import com.keilacouto.provanivel2.model.Produto;
import com.keilacouto.provanivel2.repositories.ItemPedidoRepository;
import com.keilacouto.provanivel2.repositories.PedidoRepository;
import com.keilacouto.provanivel2.repositories.ProdutoRepository;
import com.keilacouto.provanivel2.service.PedidoService;
import com.keilacouto.provanivel2.service.ProdutoService;
import com.keilacouto.provanivel2.service.impl.ItemPedidoServiceImpl;


@ExtendWith(MockitoExtension.class)
public class ItemPedidoServiceTest {

	@Mock
    private ItemPedidoRepository itemPedidoRepository;
	
	@Mock
    private PedidoRepository pedidoRepository;
	
	@Mock
    private ProdutoRepository produtoRepository;
	
	@Mock
	private PedidoService pedidoService;
	
	@Mock
	private ProdutoService produtoService;

    @InjectMocks
    private ItemPedidoServiceImpl itemPedidoService;
    
	@Test
    void should_save_one_itemPedido_tipo_produto() {
        // Preparar		
		UUID idProduto = UUID.randomUUID();
		UUID idPedido = UUID.randomUUID();
		
		Produto produto = new Produto(idProduto, "Produto 1", new BigDecimal(95.0), 
				EnumTipoProduto.PRODUTO, EnumStatusProduto.ATIVO);		
		when(produtoService.findById(idProduto)).thenReturn(produto);
		
		Pedido pedido = new Pedido(idPedido, new Date(), new BigDecimal(190.0), 
				new BigDecimal(171.0), EnumStatusPedido.ABERTO, new ArrayList<ItemPedido>());		
		when(pedidoService.findById(idPedido)).thenReturn(pedido);
		
        ItemPedido itemPedido = new ItemPedido(null, pedido, produto, 2, new BigDecimal(10), 
        		new BigDecimal(190.0), new BigDecimal(171.0));
        when(itemPedidoRepository.save(any(ItemPedido.class))).thenReturn(itemPedido);

        // Executar
        ItemPedido saved = itemPedidoService.save(itemPedido);
        
        pedidoService.update(pedido, idPedido);

        // Verificar
        assertThat(saved).isNotNull();
        
        assertThat(saved.getPedido()).isEqualTo(pedido);
        assertThat(saved.getProduto()).isEqualTo(produto);
        assertThat(saved.getQuantidade()).isEqualTo(2);
        assertThat(saved.getValorBruto()).isEqualTo(new BigDecimal(190.0));
        assertThat(saved.getValorLiquido()).isEqualTo(new BigDecimal(171.0));
        
        verify(itemPedidoRepository, times(1)).save(any(ItemPedido.class));
        
    }
	
	@Test
    void should_save_one_itemPedido_tipo_servico() {
        // Preparar		
		UUID idProduto = UUID.randomUUID();
		UUID idPedido = UUID.randomUUID();
		
		Produto produto = new Produto(idProduto, "Produto 2", new BigDecimal(350.0), 
				EnumTipoProduto.SERVICO, EnumStatusProduto.ATIVO);		
		when(produtoService.findById(idProduto)).thenReturn(produto);
		
		Pedido pedido = new Pedido(idPedido, new Date(), new BigDecimal(700.0), 
				new BigDecimal(700.0), EnumStatusPedido.ABERTO, new ArrayList<ItemPedido>());		
		when(pedidoService.findById(idPedido)).thenReturn(pedido);
		
        ItemPedido itemPedido = new ItemPedido(null, pedido, produto, 2, new BigDecimal(0), 
        		new BigDecimal(700.0), new BigDecimal(700.0));
        when(itemPedidoRepository.save(any(ItemPedido.class))).thenReturn(itemPedido);

        // Executar
        ItemPedido saved = itemPedidoService.save(itemPedido);
        
        pedidoService.update(pedido, idPedido);

        // Verificar
        assertThat(saved).isNotNull();
        
        assertThat(saved.getPedido()).isEqualTo(pedido);
        assertThat(saved.getProduto()).isEqualTo(produto);
        assertThat(saved.getQuantidade()).isEqualTo(2);
        assertThat(saved.getValorBruto()).isEqualTo(new BigDecimal(700.0));
        assertThat(saved.getValorLiquido()).isEqualTo(new BigDecimal(700.0));
        
        verify(itemPedidoRepository, times(1)).save(any(ItemPedido.class));
        
    }
	
	@Test
    void should_update_one_itemPedido() {
        // Preparar		
		UUID idProduto = UUID.randomUUID();
		UUID idPedido = UUID.randomUUID();
		UUID idItemPedido = UUID.randomUUID();
		
		Produto produto = new Produto(idProduto, "Produto 1", new BigDecimal(95.0), 
				EnumTipoProduto.PRODUTO, EnumStatusProduto.ATIVO);		
		when(produtoService.findById(idProduto)).thenReturn(produto);
		
		Pedido pedido = new Pedido(idPedido, new Date(), new BigDecimal(190.0), 
				new BigDecimal(171.0), EnumStatusPedido.ABERTO, new ArrayList<ItemPedido>());		
		when(pedidoService.findById(idPedido)).thenReturn(pedido);
		
        ItemPedido itemPedido = new ItemPedido(idItemPedido, pedido, produto, 2, new BigDecimal(10), 
        		new BigDecimal(190.0), new BigDecimal(171.0));
        when(itemPedidoRepository.findById(idItemPedido)).thenReturn(Optional.of(itemPedido));
        when(itemPedidoRepository.save(any(ItemPedido.class))).thenReturn(itemPedido);

        // Executar
        ItemPedido saved = itemPedidoService.update(itemPedido,itemPedido.getId());
        
        pedidoService.update(pedido, idPedido);

        // Verificar
        assertThat(saved).isNotNull();
        
        assertThat(saved.getPedido()).isEqualTo(pedido);
        assertThat(saved.getProduto()).isEqualTo(produto);
        assertThat(saved.getQuantidade()).isEqualTo(2);
        assertThat(saved.getValorBruto()).isEqualTo(new BigDecimal(190.0));
        assertThat(saved.getValorLiquido()).isEqualTo(new BigDecimal(171.0));
        
        verify(itemPedidoRepository, times(1)).save(any(ItemPedido.class));
        
    }	
	
	@Test
    void should_update_one_itemPedido_status_fechado() {
        // Preparar		
		UUID idProduto = UUID.randomUUID();
		UUID idPedido = UUID.randomUUID();
		UUID idItemPedido = UUID.randomUUID();
		
		Produto produto = new Produto(idProduto, "Produto 1", new BigDecimal(95.0), 
				EnumTipoProduto.PRODUTO, EnumStatusProduto.ATIVO);		
		when(produtoService.findById(idProduto)).thenReturn(produto);
		
		Pedido pedido = new Pedido(idPedido, new Date(), new BigDecimal(190.0), 
				new BigDecimal(190.0), EnumStatusPedido.FECHADO, new ArrayList<ItemPedido>());		
		when(pedidoService.findById(idPedido)).thenReturn(pedido);
		
        ItemPedido itemPedido = new ItemPedido(idItemPedido, pedido, produto, 2, new BigDecimal(15), 
        		new BigDecimal(190.0), new BigDecimal(190.0));
        when(itemPedidoRepository.findById(idItemPedido)).thenReturn(Optional.of(itemPedido));
        when(itemPedidoRepository.save(any(ItemPedido.class))).thenReturn(itemPedido);

        // Executar
        ItemPedido saved = itemPedidoService.update(itemPedido,itemPedido.getId());
        
        pedidoService.update(pedido, idPedido);

        // Verificar
        assertThat(saved).isNotNull();
        
        assertThat(saved.getPedido()).isEqualTo(pedido);
        assertThat(saved.getProduto()).isEqualTo(produto);
        assertThat(saved.getQuantidade()).isEqualTo(2);
        assertThat(saved.getValorBruto()).isEqualTo(new BigDecimal(190.0));
        assertThat(saved.getValorLiquido()).isEqualTo(new BigDecimal(190.0));
        
        verify(itemPedidoRepository, times(1)).save(any(ItemPedido.class));
        
    }	
	
	@Test
    void should_not_found_a_itemPedido_that_doesnt_exists() {
        // Preparar
        when(itemPedidoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Executa e verifica
        Assertions.assertThrows(ItemPedidoNotFoundException.class, () -> {        	
        	itemPedidoService.findById(UUID.randomUUID());        	
        });
        
        verify(itemPedidoRepository, times(1)).findById(any(UUID.class));
        
    }
	
	@Test
    void should_not_update_when_itemPedido_doesnt_exists() {
        // Preparar
        when(itemPedidoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Executa e verifica
        Assertions.assertThrows(ItemPedidoNotFoundException.class, () -> {        	
        	itemPedidoService.update(any(ItemPedido.class), UUID.randomUUID());        	
        });
        
        verify(itemPedidoRepository, times(1)).findById(any(UUID.class));
        
    }
	
	@Test
    void should_not_delete_when_itemPedido_doesnt_exists() {
        // Preparar
        when(itemPedidoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Executa e verifica
        Assertions.assertThrows(ItemPedidoNotFoundException.class, () -> {        	
        	itemPedidoService.deleteById(UUID.randomUUID());        	
        });
        
        verify(itemPedidoRepository, times(1)).findById(any(UUID.class));
        
    }
	
	@Test
    void should_found_a_itemPedido_that_exists() {
		
		// Preparar
		UUID idProduto = UUID.randomUUID();
		UUID idPedido = UUID.randomUUID();
		UUID idItemPedido = UUID.randomUUID();
		
		Produto produto = new Produto(idProduto, "Produto 1", new BigDecimal(100.0), 
				EnumTipoProduto.SERVICO, EnumStatusProduto.ATIVO);		

		Pedido pedido = new Pedido(idPedido, new Date(), new BigDecimal(100.0), 
				new BigDecimal(100.0), EnumStatusPedido.ABERTO, null);		
		
		ItemPedido itemPedido = new ItemPedido(idItemPedido, pedido, produto, 1, new BigDecimal(10), 
	        		new BigDecimal(100.0), new BigDecimal(100.0));
        when(itemPedidoRepository.findById(idItemPedido)).thenReturn(Optional.of(itemPedido));

        // Executa e verifica
        ItemPedido actual = itemPedidoService.findById(idItemPedido);
        
        assertThat(actual).isNotNull();
        
        assertThat(actual.getPedido()).isEqualTo(pedido);
        assertThat(actual.getProduto()).isEqualTo(produto);
        assertThat(actual.getQuantidade()).isEqualTo(1);
        assertThat(actual.getValorBruto()).isEqualTo(new BigDecimal(100.0));
        assertThat(actual.getValorLiquido()).isEqualTo(new BigDecimal(100.0));
        
        verify(itemPedidoRepository, times(1)).findById(idItemPedido);
        
    }
	
	@Test
    void should_delete_one_itemPedido() {
        // Preparar		
		UUID idProduto = UUID.randomUUID();
		UUID idPedido = UUID.randomUUID();
		UUID idItemPedido = UUID.randomUUID();
		
		Produto produto = new Produto(idProduto, "Produto 1", new BigDecimal(95.0), 
				EnumTipoProduto.PRODUTO, EnumStatusProduto.ATIVO);
		
		Pedido pedido = new Pedido(idPedido, new Date(), new BigDecimal(190.0), 
				new BigDecimal(171.0), EnumStatusPedido.ABERTO, new ArrayList<ItemPedido>());	
		
        ItemPedido itemPedido = new ItemPedido(idItemPedido, pedido, produto, 2, new BigDecimal(10), 
        		new BigDecimal(190.0), new BigDecimal(171.0));
        when(itemPedidoRepository.findById(idItemPedido)).thenReturn(Optional.of(itemPedido));
        
        
        // Executar
        itemPedidoService.deleteById(itemPedido.getId());
        
        // Verificar
        verify(itemPedidoRepository, times(1)).deleteById(itemPedido.getId());
	}
	
	@Test
    void should_not_add_produto_a_itemPedido_when_status_produto_is_inativo() {
       // Preparar		
		UUID idProduto = UUID.randomUUID();
		UUID idPedido = UUID.randomUUID();
		
		Produto produto = new Produto(idProduto, "Produto 1", new BigDecimal(150.0), 
				EnumTipoProduto.PRODUTO, EnumStatusProduto.INATIVO);		
		when(produtoService.findById(idProduto)).thenReturn(produto);
		
		Pedido pedido = new Pedido(idPedido, new Date(), new BigDecimal(300.0), 
				new BigDecimal(270.0), EnumStatusPedido.ABERTO, new ArrayList<ItemPedido>());		
		when(pedidoService.findById(idPedido)).thenReturn(pedido);
		
		ItemPedido itemPedido = new ItemPedido(null, pedido, produto, 2, new BigDecimal(10), 
        		new BigDecimal(300.0), new BigDecimal(270.0));        

        // Executa e verifica
        Assertions.assertThrows(ItemPedidoValidaStatusException.class, () -> {  
            itemPedidoService.save(itemPedido);
        });        
    }
	
	
}
