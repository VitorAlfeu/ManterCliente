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

@Service
public class ClienteFacade implements IClienteFacade {
	
	//private static final Logger log = LoggerFactory.getLogger(ClienteFacade.class);

	@Autowired
	private IClienteService clienteService;
	
	public ClienteDTO manterCliente(ClienteDTO clienteDTO, BindingResult result) {
		
		if (clienteDTO.getId() != 0) {
			validarClienteExistentes(clienteDTO, result);
			if(result.hasErrors()) {
				return null;
			}
		}
		
		validarCamposGerais(clienteDTO, result);
		if(result.hasErrors()) {
			return null;
		}
		
		Cliente cliente = clienteService.manterCliente(ClienteDTO.getToEntityConverter().apply(clienteDTO));
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
		if (StringUtils.isBlank(clienteDTO.getNome())) {
			result.addError(new ObjectError("cliente", String.format("O Nome do Cliente não pode ser Vazio")));
		}
		
		if (StringUtils.isBlank(clienteDTO.getRg())) {
			result.addError(new ObjectError("cliente", String.format("O RG do Cliente não pode ser Vazio")));	
		}
		
		Optional<Cliente> retorno = clienteService.findByNomeAndRg(clienteDTO.getNome(), clienteDTO.getRg());
		if (retorno.isPresent()) {
			result.addError(new ObjectError("cliente", String.format("Já existe um Cliente com o Nome: '" + clienteDTO.getNome() + "' e com o RG: '" + clienteDTO.getRg() + "'")));
			return;	
		}
	}

	public ResponseEntity<String> excluirCliente(long id) {
		try {
			Optional<Cliente> cliente = clienteService.findById(id); 
			if(!cliente.isPresent()) {
				return ResponseEntity.badRequest().body("Nenhum Cliente encontrado com o Id: " + String.valueOf(id));
			} 
			clienteService.delete(cliente);
			return ResponseEntity.ok().body("Cliente excluído com sucesso!");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Erro ao excluir Cliente  " + e);
		}
	}

	public List<ClienteDTO> buscarTodosClientes() {
		List<Cliente> lstCliente = clienteService.findAll();
		List<ClienteDTO> lstClienteDTO = new ArrayList<>();
		
		lstCliente.forEach(cliente -> lstClienteDTO.add(new ClienteDTO.ToDTO().apply(cliente)));
		
		return lstClienteDTO;
	}
	
	public List<ClienteDTO> buscarTodosClientesJDBC() {
		List<Cliente> lstCliente = clienteService.findAllJDBC();
		List<ClienteDTO> lstClienteDTO = new ArrayList<>();
		
		lstCliente.forEach(cliente -> lstClienteDTO.add(new ClienteDTO.ToDTO().apply(cliente)));
		
		return lstClienteDTO;
	}

	public ResponseEntity<Response<ClienteDTO>> buscarClientePorId(String id) {
		Response<ClienteDTO> response = new Response<>();
		Optional<Cliente> cliente = clienteService.findById(Long.parseLong(id));
		List<ClienteDTO> lstRetorno= new LinkedList<>();
		
		if (cliente.isPresent()) {
			lstRetorno.add(new ClienteDTO.ToDTO().apply(cliente.get()));	
		} else {
			response.getErrors().add("Nenhum Usuário encontrado com o Id: " + id);
		}

		response.setData(lstRetorno);
		
		return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<Response<ClienteDTO>> buscarClientePorNome(String nome){
		Response<ClienteDTO> response = new Response<>();
		Optional<Cliente> cliente = clienteService.findByNome(nome);
		List<ClienteDTO> lstRetorno= new LinkedList<>();
		
		if (cliente.isPresent()) {
			lstRetorno.add(new ClienteDTO.ToDTO().apply(cliente.get()));	
		} else {
			response.getErrors().add("Nenhum Usuário encontrado com o Nome: " + nome);	
		}

		response.setData(lstRetorno);
		
		return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<Response<ClienteDTO>> buscarClientePorRg(String rg){
		Response<ClienteDTO> response = new Response<>();
		Optional<Cliente> cliente = clienteService.findByRg(rg);
		List<ClienteDTO> lstRetorno= new LinkedList<>();
		
		if (cliente.isPresent()) {
			lstRetorno.add(new ClienteDTO.ToDTO().apply(cliente.get()));	
		} else {
			response.getErrors().add("Nenhum Usuário encontrado com o Rg: " + rg);	
		}

		response.setData(lstRetorno);
		
		return ResponseEntity.ok(response);
	}
}
