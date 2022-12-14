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

import com.keilacouto.provanivel2.exceptions.ProdutoNotFoundException;
import com.keilacouto.provanivel2.model.Produto;
import com.keilacouto.provanivel2.service.ProdutoService;

@RestController
@RequestMapping(path = "/api/produto")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;	

	@GetMapping
	public List<Produto> findAll() {
		return produtoService.findAll();
	}

	@GetMapping("/page")
	public Page<Produto> findAll(Pageable pageable) {
		return produtoService.findAll(pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> findById(@PathVariable UUID id) {
		try {
			return ResponseEntity.ok(produtoService.findById(id));
		} catch (ProdutoNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable UUID id) {
		
		try {
			produtoService.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (ProdutoNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<Produto> create(@RequestBody Produto entity) {
		Produto savedEntity = produtoService.save(entity);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedEntity.getId()).toUri();

		return ResponseEntity.created(location).build();

	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@RequestBody Produto entity, @PathVariable UUID id) {

		try {
			produtoService.update(entity, id);
		} catch (ProdutoNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}

}