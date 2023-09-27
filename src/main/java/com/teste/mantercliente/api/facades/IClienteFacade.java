package com.teste.mantercliente.api.facades;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.teste.mantercliente.api.dtos.ClienteDTO;
import com.teste.mantercliente.api.response.Response;

public interface IClienteFacade {

	/**
	 * Cadastra ou atualiza um cliente.
	 * 
	 * @param clienteDTO O DTO contendo as informações do cliente a ser cadastrado ou atualizado.
	 * @param result      O resultado da validação do DTO.
	 * @return O DTO do cliente cadastrado ou atualizado.
	 */
	ClienteDTO manterCliente(ClienteDTO clienteDTO, BindingResult result);

	/**
	 * Exclui um cliente por ID.
	 * 
	 * @param id O ID do cliente a ser excluído.
	 * @return Um ResponseEntity com o resultado da operação.
	 */
	ResponseEntity<String> excluirCliente(long id);

	/**
	 * Retorna a lista de todos os clientes.
	 * 
	 * @return Uma lista de objetos ClienteDTO representando todos os clientes.
	 */
	List<ClienteDTO> buscarTodosClientes();
	
	/**
	 * Retorna a lista de todos os clientes usando JDBC.
	 * 
	 * @return Uma lista de objetos ClienteDTO representando todos os clientes.
	 */
	List<ClienteDTO> buscarTodosClientesJDBC();

	/**
	 * Busca um cliente por ID.
	 * 
	 * @param id O ID do cliente a ser buscado.
	 * @return Um ResponseEntity com o resultado da operação.
	 */
	ResponseEntity<Response<ClienteDTO>> buscarClientePorId(String id);

	/**
	 * Busca um cliente por nome.
	 * 
	 * @param nome O nome do cliente a ser buscado.
	 * @return Um ResponseEntity com o resultado da operação.
	 */
	ResponseEntity<Response<ClienteDTO>> buscarClientePorNome(String nome);

	/**
	 * Busca um cliente por RG.
	 * 
	 * @param rg O RG do cliente a ser buscado.
	 * @return Um ResponseEntity com o resultado da operação.
	 */
	ResponseEntity<Response<ClienteDTO>> buscarClientePorRg(String rg);
}
