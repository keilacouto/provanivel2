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

import com.keilacouto.provanivel2.exceptions.EmployeeNotFoundException;
import com.keilacouto.provanivel2.model.Employee;
import com.keilacouto.provanivel2.service.EmployeeService;

@RestController
@RequestMapping(path = "/api/employee")
public class EmployeeController {

	@Autowired 
	private EmployeeService service;

	@GetMapping
    public List<Employee> findAll() {
        return service.findAll();
    }
	
	@GetMapping("/page")
	public Page<Employee> findAll(Pageable pageable) {
		return service.findAll(pageable);
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable UUID id) {
       try {
	   		return ResponseEntity.ok(service.findById(id));
	   	}catch (EmployeeNotFoundException e) {
	   		return ResponseEntity.notFound().build();
	   	}
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable UUID id) {
		service.deleteById(id);
		try {
			service.deleteById(id);
			
			return ResponseEntity.noContent().build();
			
	   	}catch (EmployeeNotFoundException e) {
	   		
	   		return ResponseEntity.notFound().build();
	   		
	   	}
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee entity) {
    	Employee savedEntity = service.save(entity);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedEntity.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Employee entity, @PathVariable UUID id) {

    	try {
    		service.update(entity,id);
    	}catch (EmployeeNotFoundException e) {
    		return ResponseEntity.notFound().build();
		}

        return ResponseEntity.noContent().build();
    }
	

}