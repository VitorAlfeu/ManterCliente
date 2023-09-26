package com.teste.mantercliente.api.repositories;

import java.util.List;

import com.teste.mantercliente.api.entities.Cliente;

public interface IClienteRepositoryJDBC {

	List<Cliente> findAllJDBC();
	
}