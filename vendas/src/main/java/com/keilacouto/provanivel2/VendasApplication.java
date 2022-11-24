package com.keilacouto.provanivel2;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.keilacouto.provanivel2.enums.EnumStatusPedido;
import com.keilacouto.provanivel2.enums.EnumStatusProduto;
import com.keilacouto.provanivel2.enums.EnumTipoProduto;
import com.keilacouto.provanivel2.model.ItemPedido;
import com.keilacouto.provanivel2.model.Pedido;
import com.keilacouto.provanivel2.model.Produto;
import com.keilacouto.provanivel2.repositories.ItemPedidoRepository;
import com.keilacouto.provanivel2.repositories.PedidoRepository;
import com.keilacouto.provanivel2.repositories.ProdutoRepository;

@SpringBootApplication
public class VendasApplication {
	
//	@Bean
//	public CommandLineRunner init(
//			@Autowired PedidoRepository pedidoRepository,
//			@Autowired ProdutoRepository produtoRepository,
//			@Autowired ItemPedidoRepository itemPedidoRepository) {
//		
//		return args -> {
//			
//			Produto produto = new Produto();
//			produto.setDescricao("Produto 1");
//			produto.setTipo(EnumTipoProduto.PRODUTO);
//			produto.setStatus(EnumStatusProduto.ATIVO);
//			produto.setValorUnitario(new BigDecimal(100));
//			
//			produtoRepository.save(produto);
//			
//			Pedido pedido = new Pedido();
//			pedido.setDataPedido(new Date());
//			//pedido.setValorTotalBruto(new BigDecimal(100));
//			//pedido.setValorTotalLiquido(new BigDecimal(100));
//			pedido.setStatus(EnumStatusPedido.ABERTO);
//			
//			pedidoRepository.save(pedido);
//			
//			ItemPedido itemPedido = new ItemPedido();
//			itemPedido.setPedido(pedido);
//			itemPedido.setProduto(produto);
//			itemPedido.setQuantidade(1);
//			itemPedido.setPercentualDesconto(new BigDecimal(10));
//			//itemPedido.setValorBruto(new BigDecimal(100));
//			//itemPedido.setValorLiquido(new BigDecimal(100));
//			
//			itemPedidoRepository.save(itemPedido);
//			
//		};
//	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}