package com.teste.mantercliente.api.facades;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.teste.mantercliente.api.dtos.ClienteDTO;
import com.teste.mantercliente.api.response.Response;

public interface IClienteFacade {

	ClienteDTO manterCliente(ClienteDTO clienteDTO, BindingResult result);

	ResponseEntity<String> excluirCliente(long id);

	List<ClienteDTO> buscarTodosClientes();

	ResponseEntity<Response<ClienteDTO>> buscarClientePorId(String id);

	ResponseEntity<Response<ClienteDTO>> buscarClientePorNome(String nome);

	ResponseEntity<Response<ClienteDTO>> buscarClientePorRg(String rg);

	List<ClienteDTO> buscarTodosClientesJDBC();
	
}
