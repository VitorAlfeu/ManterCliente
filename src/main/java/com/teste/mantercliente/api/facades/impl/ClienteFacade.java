package com.teste.mantercliente.api.facades.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.teste.mantercliente.api.dtos.ClienteDTO;
import com.teste.mantercliente.api.entities.Cliente;
import com.teste.mantercliente.api.facades.IClienteFacade;
import com.teste.mantercliente.api.response.Response;
import com.teste.mantercliente.api.services.IClienteService;

import io.micrometer.common.util.StringUtils;

/**
 * Implementação da interface IClienteFacade que fornece métodos para manipular clientes.
 */
@Service
public class ClienteFacade implements IClienteFacade {
	
	@Autowired
	private IClienteService clienteService;
	
	/**
	 * Cadastra ou atualiza um cliente.
	 * 
	 * @param clienteDTO O DTO contendo as informações do cliente a ser cadastrado ou atualizado.
	 * @param result      O resultado da validação do DTO.
	 * @return O DTO do cliente cadastrado ou atualizado.
	 */
	public ClienteDTO manterCliente(ClienteDTO clienteDTO, BindingResult result) {
	    // Verifica se o ID do cliente no DTO é diferente de zero (o que indica uma atualização)
	    if (clienteDTO.getId() != 0) {
	        validarClienteExistentes(clienteDTO, result);

	        // Se houver erros de validação, retorna null
	        if (result.hasErrors()) {
	            return null;
	        }
	    }

	    validarCamposGerais(clienteDTO, result);

	    // Se houver erros de validação, retorna null
	    if (result.hasErrors()) {
	        return null;
	    }

	    // Converte o DTO em uma entidade Cliente e a persiste no serviço
	    Cliente cliente = clienteService.manterCliente(ClienteDTO.getToEntityConverter().apply(clienteDTO));

	    // Converte a entidade Cliente resultante de volta para um DTO
	    ClienteDTO retorno = ClienteDTO.getToDTOConverter().apply(cliente);

	    return retorno;
	}

	
	private void validarClienteExistentes(ClienteDTO clienteDTO, BindingResult result) {
		if (!clienteService.findById(clienteDTO.getId()).isPresent()) {
			result.addError(new ObjectError("cliente", String.format("Cliente não encontrado com o Id: " + clienteDTO.getId())));
			return;
		}
	}

	private void validarCamposGerais(ClienteDTO clienteDTO, BindingResult result) {
		// Verifica se o campo "nome" está em branco e adiciona um erro ao resultado se estiver.
		if (StringUtils.isBlank(clienteDTO.getNome())) {
			result.addError(new ObjectError("cliente", String.format("O Nome do Cliente não pode ser Vazio")));
		}
		
		// Verifica se o campo "rg" está em branco e adiciona um erro ao resultado se estiver.
		if (StringUtils.isBlank(clienteDTO.getRg())) {
			result.addError(new ObjectError("cliente", String.format("O RG do Cliente não pode ser Vazio")));	
		} else {
			Optional<Cliente> clienteValidaRG = clienteService.findByRg(clienteDTO.getRg());
			
			// Verifica se já existe um cliente com o mesmo RG no sistema.
			// Se existir e não for o próprio cliente (caso de atualização), adiciona um erro ao resultado.
			if ((clienteValidaRG.isPresent()) 
					&& ((clienteDTO.getId() == 0) 
							|| (clienteValidaRG.get().getId() != clienteDTO.getId()))) {
				result.addError(new ObjectError("cliente", String.format("O RG: " + clienteDTO.getRg() + " é único e já pertence ao Cliente: '" + clienteValidaRG.get().getNome() + "'")));
			}
		}
		
		Optional<Cliente> retorno = clienteService.findByNomeAndRg(clienteDTO.getNome(), clienteDTO.getRg());
		// Verifica se já existe um cliente com o mesmo nome e RG no sistema.
		// Se existir e não for o próprio cliente (caso de atualização), adiciona um erro ao resultado.
		if ((retorno.isPresent()) && (clienteDTO.getId() != retorno.get().getId())) {
			result.addError(new ObjectError("cliente", String.format("Já existe um Cliente com o Nome: '" + clienteDTO.getNome() + "' e com o RG: '" + clienteDTO.getRg() + "'")));
			return;	
		}
	}

	/**
	 * Exclui um cliente por ID.
	 * 
	 * @param id O ID do cliente a ser excluído.
	 * @return Um ResponseEntity com o resultado da operação.
	 */
	public ResponseEntity<String> excluirCliente(long id) {
	    try {
	        // Tenta encontrar o cliente com o ID fornecido.
	        Optional<Cliente> cliente = clienteService.findById(id);
	        
	        // Verifica se o cliente não foi encontrado e retorna uma resposta de erro se for o caso.
	        if (!cliente.isPresent()) {
	            return ResponseEntity.badRequest().body("Nenhum Cliente encontrado com o Id: " + String.valueOf(id));
	        }
	        
	        // Exclui o cliente encontrado.
	        clienteService.delete(cliente);
	        
	        // Retorna uma resposta de sucesso.
	        return ResponseEntity.ok().body("Cliente excluído com sucesso!");
	    } catch (Exception e) {
	        // Em caso de exceção, retorna uma resposta de erro com a mensagem de erro.
	        return ResponseEntity.badRequest().body("Erro ao excluir Cliente  " + e);
	    }
	}

	/**
	 * Retorna a lista de todos os clientes.
	 * 
	 * @return Uma lista de objetos ClienteDTO representando todos os clientes.
	 */
	public List<ClienteDTO> buscarTodosClientes() {
	    // Recupera todos os clientes da camada de serviço.
	    List<Cliente> lstCliente = clienteService.findAll();
	    
	    // Inicializa uma lista para armazenar os objetos DTO dos clientes.
	    List<ClienteDTO> lstClienteDTO = new ArrayList<>();
	    
	    // Converte cada cliente em um objeto DTO e adiciona à lista de retorno.
	    lstCliente.forEach(cliente -> lstClienteDTO.add(new ClienteDTO.ToDTO().apply(cliente)));
	    
	    // Retorna a lista de objetos ClienteDTO.
	    return lstClienteDTO;
	}
	
	/**
	 * Retorna a lista de todos os clientes usando JDBC.
	 * 
	 * @return Uma lista de objetos ClienteDTO representando todos os clientes.
	 */
	public List<ClienteDTO> buscarTodosClientesJDBC() {
	    // Recupera todos os clientes usando JDBC Template da camada de serviço.
	    List<Cliente> lstCliente = clienteService.findAllJDBC();
	    
	    // Inicializa uma lista para armazenar os objetos DTO dos clientes.
	    List<ClienteDTO> lstClienteDTO = new ArrayList<>();
	    
	    // Converte cada cliente em um objeto DTO e adiciona à lista de retorno.
	    lstCliente.forEach(cliente -> lstClienteDTO.add(new ClienteDTO.ToDTO().apply(cliente)));
	    
	    // Retorna a lista de objetos ClienteDTO.
	    return lstClienteDTO;
	}

	/**
	 * Busca um cliente por ID.
	 * 
	 * @param id O ID do cliente a ser buscado.
	 * @return Um ResponseEntity com o resultado da operação.
	 */
	public ResponseEntity<Response<ClienteDTO>> buscarClientePorId(String id) {
	    // Inicializa um objeto Response para armazenar a resposta da consulta.
	    Response<ClienteDTO> response = new Response<>();
	    
	    Long clienteId = Long.parseLong(id);
	    
	    // Busca o cliente pelo ID usando a camada de serviço.
	    Optional<Cliente> cliente = clienteService.findById(clienteId);
	    
	    // Inicializa uma lista para armazenar os objetos DTO dos clientes encontrados.
	    List<ClienteDTO> lstRetorno = new LinkedList<>();
	    
	    if (cliente.isPresent()) {
	        // Se o cliente for encontrado, converte-o em um objeto DTO e adiciona à lista de retorno.
	        lstRetorno.add(new ClienteDTO.ToDTO().apply(cliente.get()));	
	    } else {
	        // Se o cliente não for encontrado, adiciona uma mensagem de erro à lista de erros do Response.
	        response.getErrors().add("Nenhum Usuário encontrado com o Id: " + id);
	    }

	    // Define os dados do Response com a lista de DTOs dos clientes.
	    response.setData(lstRetorno);
	    
	    // Retorna um ResponseEntity com o Response contendo os dados ou mensagem de erro.
	    return ResponseEntity.ok(response);
	}
	
	/**
	 * Busca um cliente por nome.
	 * 
	 * @param nome O nome do cliente a ser buscado.
	 * @return Um ResponseEntity com o resultado da operação.
	 */
	public ResponseEntity<Response<ClienteDTO>> buscarClientePorNome(String nome) {
	    // Inicializa um objeto Response para armazenar a resposta da consulta.
	    Response<ClienteDTO> response = new Response<>();
	    
	    // Busca o cliente pelo nome usando a camada de serviço.
	    Optional<Cliente> cliente = clienteService.findByNome(nome);
	    
	    // Inicializa uma lista para armazenar os objetos DTO dos clientes encontrados.
	    List<ClienteDTO> lstRetorno = new LinkedList<>();
	    
	    if (cliente.isPresent()) {
	        // Se o cliente for encontrado, converte-o em um objeto DTO e adiciona à lista de retorno.
	        lstRetorno.add(new ClienteDTO.ToDTO().apply(cliente.get()));	
	    } else {
	        // Se o cliente não for encontrado, adiciona uma mensagem de erro à lista de erros do Response.
	        response.getErrors().add("Nenhum Usuário encontrado com o Nome: " + nome);	
	    }

	    // Define os dados do Response com a lista de DTOs dos clientes.
	    response.setData(lstRetorno);
	    
	    // Retorna um ResponseEntity com o Response contendo os dados ou mensagem de erro.
	    return ResponseEntity.ok(response);
	}
	
	/**
	 * Busca um cliente por RG.
	 * 
	 * @param rg O RG do cliente a ser buscado.
	 * @return Um ResponseEntity com o resultado da operação.
	 */
	public ResponseEntity<Response<ClienteDTO>> buscarClientePorRg(String rg) {
	    // Inicializa um objeto Response para armazenar a resposta da consulta.
	    Response<ClienteDTO> response = new Response<>();
	    
	    // Busca o cliente pelo RG usando a camada de serviço.
	    Optional<Cliente> cliente = clienteService.findByRg(rg);
	    
	    // Inicializa uma lista para armazenar os objetos DTO dos clientes encontrados.
	    List<ClienteDTO> lstRetorno = new LinkedList<>();
	    
	    if (cliente.isPresent()) {
	        // Se o cliente for encontrado, converte-o em um objeto DTO e adiciona à lista de retorno.
	        lstRetorno.add(new ClienteDTO.ToDTO().apply(cliente.get()));	
	    } else {
	        // Se o cliente não for encontrado, adiciona uma mensagem de erro à lista de erros do Response.
	        response.getErrors().add("Nenhum Usuário encontrado com o Rg: " + rg);	
	    }

	    // Define os dados do Response com a lista de DTOs dos clientes.
	    response.setData(lstRetorno);
	    
	    // Retorna um ResponseEntity com o Response contendo os dados ou mensagem de erro.
	    return ResponseEntity.ok(response);
	}
}
