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

import com.keilacouto.provanivel2.exceptions.PedidoNotFoundException;
import com.keilacouto.provanivel2.model.Pedido;
import com.keilacouto.provanivel2.service.PedidoService;

@RestController
@RequestMapping(path = "/api/pedido")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@GetMapping
	public List<Pedido> findAll() {
		return pedidoService.findAll();
	}

	@GetMapping("/page")
	public Page<Pedido> findAll(Pageable pageable) {
		return pedidoService.findAll(pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pedido> findById(@PathVariable UUID id) {
		try {
			return ResponseEntity.ok(pedidoService.findById(id));
		} catch (PedidoNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable UUID id) {

		try {
			pedidoService.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (PedidoNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<Pedido> create(@RequestBody Pedido entity) {
		Pedido savedEntity = pedidoService.save(entity);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedEntity.getId()).toUri();

		return ResponseEntity.created(location).build();

	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@RequestBody Pedido entity, @PathVariable UUID id) {

		try {
			pedidoService.update(entity, id);
		} catch (PedidoNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}

}