package com.teste.mantercliente.api.services;

import java.util.List;
import java.util.Optional;

import com.teste.mantercliente.api.entities.Cliente;

public interface IClienteService {

	Cliente manterCliente(Cliente cliente);

	Optional<Cliente> findById(long id);

	void delete(Optional<Cliente> cliente);
	
	List<Cliente> findAll();
	
	List<Cliente> findAllJDBC();

	Optional<Cliente> findByNome(String nome);

	Optional<Cliente> findByRg(String rg);
	
	Optional<Cliente> findByNomeAndRg(String nome, String rg);
}
