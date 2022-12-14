package com.keilacouto.provanivel2.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.keilacouto.provanivel2.exceptions.ItemPedidoNotFoundException;
import com.keilacouto.provanivel2.model.ItemPedido;
import com.keilacouto.provanivel2.service.ItemPedidoService;

@RestController
@RequestMapping(path = "/api/itempedido")
public class ItemPedidoController {

	@Autowired
	private ItemPedidoService itemPedidoService;

	@GetMapping
	public List<ItemPedido> findAll() {
		return itemPedidoService.findAll();
	}

	@GetMapping("/page")
	public Page<ItemPedido> findAll(Pageable pageable) {
		return itemPedidoService.findAll(pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ItemPedido> findById(@PathVariable UUID id) {
		try {
			return ResponseEntity.ok(itemPedidoService.findById(id));
		} catch (ItemPedidoNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable UUID id) {

		try {
			itemPedidoService.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (ItemPedidoNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<ItemPedido> create(@RequestBody ItemPedido entity) {

		ItemPedido savedEntity = itemPedidoService.save(entity);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedEntity.getId()).toUri();

		return ResponseEntity.created(location).build();

	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@RequestBody ItemPedido entity, @PathVariable UUID id) {

		try {
			itemPedidoService.update(entity, id);
		} catch (ItemPedidoNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}

}