package com.teste.mantercliente.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.mantercliente.api.entities.Cliente;
import com.teste.mantercliente.api.repositories.IClienteRepository;
import com.teste.mantercliente.api.repositories.IClienteRepositoryJDBC;
import com.teste.mantercliente.api.services.IClienteService;

/**
 * Implementação da interface IClienteService que fornece serviços relacionados aos clientes.
 */
@Service
public class ClienteService implements IClienteService {
    
    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);
    
    @Autowired
    IClienteRepository clienteRepository;
    
    @Autowired
    IClienteRepositoryJDBC clienteReposirotyJDBC;
    
    /**
     * Cadastra ou atualiza um cliente.
     *
     * @param cliente O objeto Cliente a ser cadastrado ou atualizado.
     * @return O cliente após o cadastro ou atualização.
     */
    public Cliente manterCliente(Cliente cliente) {
        log.info("Cadastrando/Atualizando Cliente...");
        return this.clienteRepository.save(cliente);
    }

    /**
     * Busca um cliente pelo seu ID.
     *
     * @param id O ID do cliente a ser buscado.
     * @return Um Optional contendo o cliente encontrado ou vazio se não encontrado.
     */
    public Optional<Cliente> findById(long id) {
        log.info("Buscando Cliente por Id...");
        return this.clienteRepository.findById(id);
    }
    
    /**
     * Busca um cliente pelo seu nome.
     *
     * @param nome O nome do cliente a ser buscado.
     * @return Um Optional contendo o cliente encontrado ou vazio se não encontrado.
     */
    public Optional<Cliente> findByNome(String nome){
        log.info("Buscando Cliente por Nome...");
        return this.clienteRepository.findByNome(nome);
    }
    
    /**
     * Busca um cliente pelo seu RG.
     *
     * @param rg O RG do cliente a ser buscado.
     * @return Um Optional contendo o cliente encontrado ou vazio se não encontrado.
     */
    public Optional<Cliente> findByRg(String rg){
        log.info("Buscando Cliente por Rg...");
        return this.clienteRepository.findByRg(rg);	
    }
    
    /**
     * Busca um cliente pelo nome e RG.
     *
     * @param nome O nome do cliente a ser buscado.
     * @param rg   O RG do cliente a ser buscado.
     * @return Um Optional contendo o cliente encontrado ou vazio se não encontrado.
     */
    public Optional<Cliente> findByNomeAndRg(String nome, String rg){
        log.info("Buscando Cliente por Nome e Rg...");
        return this.clienteRepository.findByNomeAndRg(nome, rg);
    }

    /**
     * Exclui um cliente.
     *
     * @param cliente O cliente a ser excluído.
     */
    public void delete(Optional<Cliente> cliente) {
        log.info("Excluindo Usuário por Id...");
        cliente.ifPresent(c -> this.clienteRepository.delete(c));
    }

    /**
     * Retorna uma lista de todos os clientes.
     *
     * @return Uma lista de objetos Cliente.
     */
    public List<Cliente> findAll() {
        log.info("Buscando todos os Clientes...");
        return this.clienteRepository.findAll();
    }
    
    /**
     * Retorna uma lista de todos os clientes usando o JDBC Template.
     *
     * @return Uma lista de objetos Cliente.
     */
    public List<Cliente> findAllJDBC(){
        log.info("Buscando todos os Clientes com JDBC Template...");
        return this.clienteReposirotyJDBC.findAllJDBC();	
    }
}