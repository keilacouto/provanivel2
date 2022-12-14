package com.keilacouto.provanivel2.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.keilacouto.provanivel2.enums.EnumStatusPedido;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "pedido")
public class Pedido {

	@Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
	private UUID id;

	@Column(name = "data_pedido")
	private Date dataPedido;

	@Column(name = "valor_total_bruto")
	private BigDecimal valorTotalBruto;

	@Column(name = "valor_total_liquido")
	private BigDecimal valorTotalLiquido;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private EnumStatusPedido status;
	
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itensPedido;
	
	public Pedido() {

	}
	
	public Pedido(UUID id, Date dataPedido, BigDecimal valorTotalBruto, BigDecimal valorTotalLiquido,
			EnumStatusPedido status, List<ItemPedido> itensPedido) {
		super();
		this.id = id;
		this.dataPedido = dataPedido;
		this.valorTotalBruto = valorTotalBruto;
		this.valorTotalLiquido = valorTotalLiquido;
		this.status = status;
		this.itensPedido = itensPedido;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public BigDecimal getValorTotalBruto() {
		return valorTotalBruto;
	}

	public void setValorTotalBruto(BigDecimal valorTotalBruto) {
		this.valorTotalBruto = valorTotalBruto;
	}

	public BigDecimal getValorTotalLiquido() {
		return valorTotalLiquido;
	}

	public void setValorTotalLiquido(BigDecimal valorTotalLiquido) {
		this.valorTotalLiquido = valorTotalLiquido;
	}

	public EnumStatusPedido getStatus() {
		return status;
	}

	public void setStatus(EnumStatusPedido status) {
		this.status = status;
	}
	
	public List<ItemPedido> getItensPedido() {
		return itensPedido;
	}
	
	public void setItensPedido(List<ItemPedido> itensPedido) {
		this.itensPedido = itensPedido;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", dataPedido=" + dataPedido + ", valorTotalBruto=" + valorTotalBruto
				+ ", valorTotalLiquido=" + valorTotalLiquido + ", status=" + status + "]";
	}

}
