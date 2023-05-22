package br.com.santander.internetbanking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.santander.internetbanking.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	Cliente findByNumeroConta(String numeroConta);
	
	Page<Cliente> findAll(Pageable paginacao);
    
}
