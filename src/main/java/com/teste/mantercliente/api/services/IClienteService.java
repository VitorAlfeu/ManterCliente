package com.teste.mantercliente.api.services;

import java.util.List;
import java.util.Optional;

import com.teste.mantercliente.api.entities.Cliente;

public interface IClienteService {

    /**
     * Cadastra ou atualiza um cliente.
     *
     * @param cliente O objeto Cliente a ser cadastrado ou atualizado.
     * @return O cliente após o cadastro ou atualização.
     */
	Cliente manterCliente(Cliente cliente);

    /**
     * Busca um cliente pelo seu ID.
     *
     * @param id O ID do cliente a ser buscado.
     * @return Um Optional contendo o cliente encontrado ou vazio se não encontrado.
     */
	Optional<Cliente> findById(long id);

	/**
     * Busca um cliente pelo seu nome.
     *
     * @param nome O nome do cliente a ser buscado.
     * @return Um Optional contendo o cliente encontrado ou vazio se não encontrado.
     */
	Optional<Cliente> findByNome(String nome);
	
    /**
     * Busca um cliente pelo seu RG.
     *
     * @param rg O RG do cliente a ser buscado.
     * @return Um Optional contendo o cliente encontrado ou vazio se não encontrado.
     */
	Optional<Cliente> findByRg(String rg);
	
    /**
     * Busca um cliente pelo nome e RG.
     *
     * @param nome O nome do cliente a ser buscado.
     * @param rg   O RG do cliente a ser buscado.
     * @return Um Optional contendo o cliente encontrado ou vazio se não encontrado.
     */
	Optional<Cliente> findByNomeAndRg(String nome, String rg);
	
    /**
     * Exclui um cliente.
     *
     * @param cliente O cliente a ser excluído.
     */
	void delete(Optional<Cliente> cliente);
	
    /**
     * Retorna uma lista de todos os clientes.
     *
     * @return Uma lista de objetos Cliente.
     */
	List<Cliente> findAll();
	
    /**
     * Retorna uma lista de todos os clientes usando o JDBC Template.
     *
     * @return Uma lista de objetos Cliente.
     */
	List<Cliente> findAllJDBC();

}
