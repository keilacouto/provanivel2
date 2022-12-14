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
import com.keilacouto.provanivel2.exceptions.PedidoNotFoundException;
import com.keilacouto.provanivel2.model.ItemPedido;
import com.keilacouto.provanivel2.model.Pedido;
import com.keilacouto.provanivel2.model.Produto;
import com.keilacouto.provanivel2.repositories.PedidoRepository;
import com.keilacouto.provanivel2.service.impl.PedidoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {
	
	@Mock
	private PedidoRepository pedidoRepository;
	
	@InjectMocks
    private PedidoServiceImpl pedidoService;
	
	@Test
	void should_save_one_pedido() {
	
		//Preparar
		Date dataPedido = new Date();
		UUID idProduto = UUID.randomUUID();
		UUID idPedido = UUID.randomUUID();
		
		Produto produto = new Produto(idProduto, "Produto 1", new BigDecimal(95.0), 
				EnumTipoProduto.PRODUTO, EnumStatusProduto.ATIVO);			
		
		Pedido pedido = new Pedido(idPedido, dataPedido, new BigDecimal(190.0), 
				new BigDecimal(171.0), EnumStatusPedido.ABERTO, new ArrayList<ItemPedido>());		
				
        ItemPedido itemPedido = new ItemPedido(null, pedido, produto, 2, new BigDecimal(10), 
        		new BigDecimal(190.0), new BigDecimal(171.0));
		pedido.getItensPedido().add(itemPedido);
			
		when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
		
		//Executar
		Pedido saved = pedidoService.save(pedido);
		
		//Verificar
		assertThat(saved).isNotNull();
        
        assertThat(saved.getDataPedido()).isEqualTo(dataPedido);
        assertThat(saved.getValorTotalBruto()).isEqualTo(new BigDecimal(190.0));
        assertThat(saved.getValorTotalLiquido()).isEqualTo(new BigDecimal(171.0));
        assertThat(saved.getStatus()).isEqualTo(EnumStatusPedido.ABERTO);
        assertThat(saved.getItensPedido()).hasSize(1);
        assertThat(saved.getItensPedido()).contains(itemPedido);
        
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
	}
	
	@Test
	void should_update_one_pedido() {
	
		//Preparar
		Date dataPedido = new Date();
		UUID idProduto = UUID.randomUUID();
		UUID idPedido = UUID.randomUUID();
		
		Produto produto = new Produto(idProduto, "Produto 1", new BigDecimal(95.0), 
				EnumTipoProduto.PRODUTO, EnumStatusProduto.ATIVO);			
		
		Pedido pedido = new Pedido(idPedido, dataPedido, new BigDecimal(190.0), 
				new BigDecimal(171.0), EnumStatusPedido.ABERTO, new ArrayList<ItemPedido>());		
				
        ItemPedido itemPedido = new ItemPedido(null, pedido, produto, 2, new BigDecimal(10), 
        		new BigDecimal(190.0), new BigDecimal(171.0));
		pedido.getItensPedido().add(itemPedido);
		
		when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedido));
		when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
		
		//Executar
		Pedido saved = pedidoService.update(pedido, pedido.getId());
		
		//Verificar
		assertThat(saved).isNotNull();
        
        assertThat(saved.getDataPedido()).isEqualTo(dataPedido);
        assertThat(saved.getValorTotalBruto()).isEqualTo(new BigDecimal(190.0));
        assertThat(saved.getValorTotalLiquido()).isEqualTo(new BigDecimal(171.0));
        assertThat(saved.getStatus()).isEqualTo(EnumStatusPedido.ABERTO);
        assertThat(saved.getItensPedido()).hasSize(1);
        assertThat(saved.getItensPedido()).contains(itemPedido);
        
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
	}
	
	@Test
    void should_not_update_when_pedido_doesnt_exists() {
        // Preparar
        when(pedidoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Executa e verifica
        Assertions.assertThrows(PedidoNotFoundException.class, () -> {        	
        	pedidoService.update(any(Pedido.class), UUID.randomUUID());        	
        });
        
        verify(pedidoRepository, times(1)).findById(any(UUID.class));
        
    }
	

	@Test
    void should_not_delete_when_pedido_doesnt_exists() {
        // Preparar
        when(pedidoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Executa e verifica
        Assertions.assertThrows(PedidoNotFoundException.class, () -> {        	
        	pedidoService.deleteById(UUID.randomUUID());        	
        });
        
        verify(pedidoRepository, times(1)).findById(any(UUID.class));
        
    }
	
	@Test
    void should_not_found_a_pedido_that_doesnt_exists() {
        // Preparar
        when(pedidoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Executa e verifica
        Assertions.assertThrows(PedidoNotFoundException.class, () -> {        	
        	pedidoService.findById(UUID.randomUUID());        	
        });
        
        verify(pedidoRepository, times(1)).findById(any(UUID.class));
        
    }
	
	@Test
    void should_delete_one_pedido() {
        // Preparar		
		UUID idPedido = UUID.randomUUID();
		
		Pedido pedido = new Pedido(idPedido, new Date(), BigDecimal.ZERO, 
				BigDecimal.ZERO, EnumStatusPedido.ABERTO, new ArrayList<ItemPedido>());	
        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedido));
        
        // Executar
        pedidoService.deleteById(pedido.getId());
        
        // Verificar
        verify(pedidoRepository, times(1)).deleteById(pedido.getId());
	}
	
	@Test
    void should_found_a_pedido_that_exists() {
		
		// Preparar
		UUID idPedido = UUID.randomUUID();
		Date dataPedido = new Date();
		
		Pedido pedido = new Pedido(idPedido, dataPedido, BigDecimal.ZERO, 
				BigDecimal.ZERO, EnumStatusPedido.ABERTO, new ArrayList<ItemPedido>());		
		when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedido));

        // Executa e verifica
        Pedido actual = pedidoService.findById(idPedido);
        
        assertThat(actual).isNotNull();
        
        assertThat(actual.getDataPedido()).isEqualTo(dataPedido);
        assertThat(actual.getValorTotalBruto()).isEqualTo(BigDecimal.ZERO);
        assertThat(actual.getValorTotalLiquido()).isEqualTo(BigDecimal.ZERO);
        assertThat(actual.getStatus()).isEqualTo(EnumStatusPedido.ABERTO);
        assertThat(actual.getItensPedido()).isEqualTo(new ArrayList<ItemPedido>());
        
        verify(pedidoRepository, times(1)).findById(idPedido);
        
    }
}
