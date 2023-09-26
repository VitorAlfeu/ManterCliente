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

@Service
public class ClienteService implements IClienteService{
	
	private static final Logger log = LoggerFactory.getLogger(ClienteService.class);
	
	@Autowired
	IClienteRepository clienteRepository;
	
	@Autowired
	IClienteRepositoryJDBC clienteReposirotyJDBC;
	
	public Cliente manterCliente (Cliente cliente) {
		log.info("Cadastrando/Atualizando Cliente...");
		return this.clienteRepository.save(cliente);
	}

	public Optional<Cliente> findById(long id) {
		log.info("Buscando Cliente por Id...");
		return this.clienteRepository.findById(id);
	}
	
	public Optional<Cliente> findByNome(String nome){
		log.info("Buscando Cliente por Nome...");
		return this.clienteRepository.findByNome(nome);
	}
	
	public Optional<Cliente> findByRg(String rg){
		log.info("Buscando Cliente por Rg...");
		return this.clienteRepository.findByRg(rg);	
	}
	
	public Optional<Cliente> findByNomeAndRg(String nome, String rg){
		log.info("Buscando Cliente por Nome e Rg...");
		return this.clienteRepository.findByNomeAndRg(nome, rg);
	}

	public void delete(Optional<Cliente> cliente) {
		log.info("Excluindo Usuário por Id...");
		cliente.ifPresent(u -> this.clienteRepository.delete(u));
	}

	public List<Cliente> findAll() {
		log.info("Buscando todos os Clientes...");
		return this.clienteRepository.findAll();
	}
	
	public List<Cliente> findAllJDBC(){
		log.info("Buscando todos os Clientes com JDBC Template...");
		return this.clienteReposirotyJDBC.findAllJDBC();	
	}
}