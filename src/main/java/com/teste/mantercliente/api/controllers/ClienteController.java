package com.teste.mantercliente.api.controllers;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.mantercliente.api.dtos.ClienteDTO;
import com.teste.mantercliente.api.facades.IClienteFacade;
import com.teste.mantercliente.api.response.Response;

/**
 * Controlador para gerenciar operações relacionadas aos clientes.
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	
	// Criação de um logger para registrar informações e erros
	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);
	
	@Autowired
	private IClienteFacade clienteFacade;
	
	/**
	 * Cadastra um novo cliente.
	 * 
	 * @param clienteDTO O DTO contendo as informações do cliente a ser cadastrado.
	 * @param result      O resultado da validação do DTO.
	 * @return Um ResponseEntity com o resultado da operação.
	 * @throws ParseException Se ocorrer um erro de parsing.
	 */
	@PostMapping
	public ResponseEntity<Response<ClienteDTO>> cadastrarCliente(@RequestBody ClienteDTO clienteDTO, BindingResult result) throws ParseException {
		Response<ClienteDTO> response = new Response<ClienteDTO>();
		ClienteDTO retorno = clienteFacade.manterCliente(clienteDTO, result); 

		// Verifica se há erros de validação
		if (result.hasErrors()) {
			log.error("Erro ao cadastrar Cliente: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		List<ClienteDTO> lstRetorno = new LinkedList<>();		
		lstRetorno.add(retorno);
		
		response.setData(lstRetorno);
		return ResponseEntity.ok().body(response);
	}
	
	/**
	 * Atualiza um cliente existente.
	 * 
	 * @param clienteDTO O DTO contendo as informações do cliente a ser atualizado.
	 * @param result      O resultado da validação do DTO.
	 * @return Um ResponseEntity com o resultado da operação.
	 * @throws ParseException Se ocorrer um erro de parsing.
	 */
	@PutMapping
	public ResponseEntity<Response<ClienteDTO>> atualizarCliente(@RequestBody ClienteDTO clienteDTO, BindingResult result) throws ParseException {
		Response<ClienteDTO> response = new Response<ClienteDTO>();
		
		// Verifica se o ID do cliente é válido
		if (clienteDTO.getId() == 0) {
			result.addError(new ObjectError("cliente", String.format("Para a Atualização de um Cliente o id não pode ser vazio ou igual à 0.")));
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		ClienteDTO retorno = clienteFacade.manterCliente(clienteDTO, result); 

		// Verifica se há erros de validação
		if (result.hasErrors()) {
			log.error("Erro ao cadastrar Cliente: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		List<ClienteDTO> lstRetorno = new LinkedList<>();		
		lstRetorno.add(retorno);
		
		response.setData(lstRetorno);
		return ResponseEntity.ok().body(response);
	}
	
	/**
	 * Exclui um cliente por ID.
	 * 
	 * @param id O ID do cliente a ser excluído.
	 * @return Um ResponseEntity com o resultado da operação.
	 */
	@DeleteMapping(value="/{id}")
	public ResponseEntity<String> excluirCliente(@PathVariable("id") long id) {
		return clienteFacade.excluirCliente(id);
	}
	
	/**
	 * Retorna a lista de todos os clientes.
	 * 
	 * @return Uma lista de objetos ClienteDTO representando todos os clientes.
	 */
	@GetMapping(value="/todos")
	public List<ClienteDTO> buscarTodosClientes(){
		return clienteFacade.buscarTodosClientes();
	}
	
	/**
	 * Retorna a lista de todos os clientes usando JDBC.
	 * 
	 * @return Uma lista de objetos ClienteDTO representando todos os clientes.
	 */
	@GetMapping(value="/todos_jdbc")
	public List<ClienteDTO> buscarTodosClientesJDBC(){
		return clienteFacade.buscarTodosClientesJDBC();
	}
	
	/**
	 * Busca um cliente por ID.
	 * 
	 * @param id O ID do cliente a ser buscado.
	 * @return Um ResponseEntity com o resultado da operação.
	 */
	@GetMapping(value="/buscar_por_id/{id}")
	public ResponseEntity<Response<ClienteDTO>> buscarClientePorId(@PathVariable("id") String id) {
		return clienteFacade.buscarClientePorId(id);
	}
	
	/**
	 * Busca um cliente por nome.
	 * 
	 * @param nome O nome do cliente a ser buscado.
	 * @return Um ResponseEntity com o resultado da operação.
	 */
	@GetMapping(value="/buscar_por_nome/{nome}")
	public ResponseEntity<Response<ClienteDTO>> buscarClientePorNome(@PathVariable("nome") String nome) {
		return clienteFacade.buscarClientePorNome(nome);
	}
	
	/**
	 * Busca um cliente por RG.
	 * 
	 * @param rg O RG do cliente a ser buscado.
	 * @return Um ResponseEntity com o resultado da operação.
	 */
	@GetMapping(value="/buscar_por_rg/{rg}")
	public ResponseEntity<Response<ClienteDTO>> buscarClientePorRg(@PathVariable("rg") String rg) {
		return clienteFacade.buscarClientePorRg(rg);
	}
}