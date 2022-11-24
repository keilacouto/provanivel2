package com.keilacouto.provanivel2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.keilacouto.provanivel2.exceptions.EmployeeNotFoundException;
import com.keilacouto.provanivel2.model.Employee;
import com.keilacouto.provanivel2.repositories.EmployeeRepository;
import com.keilacouto.provanivel2.service.impl.EmployeeServiceImpl;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

	@Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeServiceImpl service;
    
	@Test
    void should_save_one_employee() {
        // Preparar
        Employee entity = new Employee(null,"Func test", "ti");
        when(repository.save(any(Employee.class))).thenReturn(entity);

        // Executar
        Employee saved = service.save(entity);

        // Verificar
        assertThat(saved).isNotNull();
        
        assertThat(saved.getName()).isEqualTo("Func test");
        assertThat(saved.getDept()).isEqualTo("ti");
        
        verify(repository, times(1)).save(any(Employee.class));
        
    }
	
	
	@Test
    void should_not_found_a_employee_that_doesnt_exists() {
        // Preparar
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Executa e verifica
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
        	
        	service.findById(UUID.randomUUID());
        	
        });
        
        verify(repository, times(1)).findById(any(UUID.class));
        
    }
	
	@Test
    void should_found_a_employee_that_exists() {
		
		UUID id = UUID.randomUUID();
		Employee entity = new Employee(id,"Func test", "ti");
        // Preparar
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        // Executa e verifica
        Employee actual = service.findById(id);
        
        assertThat(actual).isNotNull();
        
        assertThat(actual.getName()).isEqualTo("Func test");
        assertThat(actual.getDept()).isEqualTo("ti");
        
        verify(repository, times(1)).findById(id);
        
    }
}
