package com.keilacouto.provanivel2.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.keilacouto.provanivel2.model.Employee;

public interface EmployeeService {

	Employee save(Employee entity);
 
    List<Employee> findAll();
 
    Employee update(Employee entity,UUID id);
 
    void deleteById(UUID id);

	Employee findById(UUID id);

	Page<Employee> findAll(Pageable pageable);
}
