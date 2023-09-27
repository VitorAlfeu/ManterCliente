package com.teste.mantercliente.api.repositories;

import java.util.List;

import com.teste.mantercliente.api.entities.Cliente;

public interface IClienteRepositoryJDBC {

    /**
     * Recupera todos os clientes do banco de dados usando uma consulta SQL simples.
     *
     * @return Uma lista de objetos Cliente contendo os dados dos clientes encontrados.
     */
	List<Cliente> findAllJDBC();
	
}