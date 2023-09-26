package com.teste.mantercliente.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teste.mantercliente.api.entities.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByNome(String nome);

	Optional<Cliente> findByRg(String rg);
	
	Optional<Cliente> findByNomeAndRg(String nome, String rg);
	
}
