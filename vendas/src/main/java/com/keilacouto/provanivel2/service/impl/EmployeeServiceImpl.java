package com.keilacouto.provanivel2.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.keilacouto.provanivel2.exceptions.EmployeeNotFoundException;
import com.keilacouto.provanivel2.model.Employee;
import com.keilacouto.provanivel2.repositories.EmployeeRepository;
import com.keilacouto.provanivel2.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired 
    private EmployeeRepository repository;
	
	@Override
	public Employee save(Employee entity) {
		return repository.save(entity);
	}

	@Override
	public List<Employee> findAll() {
		 return repository.findAll();
	}
	
	@Override
	public Page<Employee> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@Override
	public Employee findById(UUID id) {
		Optional<Employee> entity = repository.findById(id);

        if (entity.isEmpty())
            throw new EmployeeNotFoundException("id-" + id);

        return entity.get();
			
	}

	@Override
	public Employee update(Employee entity, UUID id) {
		Optional<Employee> entityOptional = repository.findById(id);

        if (entityOptional.isEmpty())
        	throw new EmployeeNotFoundException("id-" + id);

        entity.setId(id);

        repository.save(entity);
        
        return entity;
	}

	@Override
	public void deleteById(UUID id) {
		Optional<Employee> entity = repository.findById(id);

        if (entity.isEmpty())
            throw new EmployeeNotFoundException("id-" + id);
        
		repository.deleteById(id);		
	}

	

}
