package com.teste.mantercliente.api.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.teste.mantercliente.api.dtos.ClienteDTO;
import com.teste.mantercliente.api.entities.Cliente;
import com.teste.mantercliente.api.facades.IClienteFacade;
import com.teste.mantercliente.api.response.Response;
import com.teste.mantercliente.api.services.IClienteService;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerUnitTest {

	@Spy
	@InjectMocks
	private ClienteController controller;
	
	@Mock
	private IClienteFacade facade;
	
	@Mock
	private IClienteService service;
	
	private static Cliente cliente;
	
	private static ClienteDTO clienteDTO;
	
	@BeforeAll
	public static void beforeAll() {
		cliente = new Cliente(100, "Vitor", "123456789");
	}
	
	@Test
	public void testCadastrarCliente() throws ParseException {
		ClienteDTO clienteDTO = mock(ClienteDTO.class);
		BindingResult bindingResult = mock(BindingResult.class);
		
		when(facade.manterCliente(clienteDTO, bindingResult)).thenReturn(clienteDTO);
		
		ResponseEntity<Response<ClienteDTO>> responseEntity = controller.cadastrarCliente(clienteDTO, bindingResult);
		
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(clienteDTO, responseEntity.getBody().getData().get(0));

        // Verificando se o método do clienteFacade foi chamado uma vez
        verify(facade, times(1)).manterCliente(clienteDTO, bindingResult);
	}
	
    @Test
    public void testAtualizarCliente() throws ParseException {
        ClienteDTO clienteDTO = new ClienteDTO();
        BindingResult bindingResult = mock(BindingResult.class);

        // Configurando o comportamento do clienteFacade
        when(facade.manterCliente(clienteDTO, bindingResult)).thenReturn(clienteDTO);

        ResponseEntity<Response<ClienteDTO>> responseEntity = controller.atualizarCliente(clienteDTO, bindingResult);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(clienteDTO, responseEntity.getBody().getData().get(0));

        // Verificando se o método do clienteFacade foi chamado uma vez
        verify(facade, times(1)).manterCliente(clienteDTO, bindingResult);
    }
    
    @Test
    public void testExcluirCliente() {
        long id = 12L; // Id de exemplo
        String mensagemExclusao = "Cliente excluído com sucesso"; // Mensagem de exemplo

        // Configurando o comportamento do clienteFacade
        when(facade.excluirCliente(id)).thenReturn(ResponseEntity.ok(mensagemExclusao));

        ResponseEntity<String> responseEntity = controller.excluirCliente(id);

        assertEquals(ResponseEntity.ok(mensagemExclusao), responseEntity);

        // Verificando se o método do clienteFacade foi chamado com o id correto
        verify(facade, times(1)).excluirCliente(id);
    }
    
    @Test
    public void testBuscarTodosClientes() {
        // Criando uma lista fictícia de ClienteDTO para retornar do mock da clienteFacade
        List<ClienteDTO> clientesFicticios = new ArrayList<>();
        clientesFicticios.add(new ClienteDTO(1, "Nome1", "12345"));
        clientesFicticios.add(new ClienteDTO(2, "Nome2", "67890"));

        // Configurando o comportamento do mock da clienteFacade
        when(facade.buscarTodosClientes()).thenReturn(clientesFicticios);

        // Chamando o método da controller que você deseja testar
        List<ClienteDTO> resultado = controller.buscarTodosClientes();

        // Verificando se o resultado é o esperado
        assertEquals(clientesFicticios, resultado);

        // Verificando se o método do mock da clienteFacade foi chamado uma vez
        verify(facade, times(1)).buscarTodosClientes();
    }
    
    @Test
    public void testBuscarTodosClientesJDBCListaVazia() {
        // Configurando o mock da clienteFacade para retornar uma lista vazia
        when(facade.buscarTodosClientesJDBC()).thenReturn(Collections.emptyList());

        // Chamando o método da controller que você deseja testar
        List<ClienteDTO> resultado = controller.buscarTodosClientesJDBC();

        // Verificando se o resultado é uma lista vazia
        assertEquals(Collections.emptyList(), resultado);

        // Verificando se o método do mock da clienteFacade foi chamado uma vez
        verify(facade, times(1)).buscarTodosClientesJDBC();
    }
    
    @Test
    public void testBuscarClientePorId() {
        String clienteId = "100";
        ClienteDTO clienteDTO = new ClienteDTO().getToDTOConverter().apply(this.cliente);

        Response<ClienteDTO> response = new Response<>();
        response.setData(new ArrayList<ClienteDTO>() {{ add(clienteDTO);}});
        
        // Configurando o comportamento do mock da clienteFacade
        when(facade.buscarClientePorId(clienteId)).thenReturn(ResponseEntity.ok(response));
        
        // Chamando o método da controller que você deseja testar
        ResponseEntity<Response<ClienteDTO>> responseEntity = controller.buscarClientePorId(clienteId);

        // Verificando se o status de resposta é OK (200)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verificando se o corpo da resposta contém o clienteDTO esperado
        assertEquals(response.getData(), responseEntity.getBody().getData());

        // Verificando se o método do mock da clienteFacade foi chamado uma vez
        verify(facade, times(1)).buscarClientePorId(clienteId);
    }
    
    @Test
    public void testBuscarClientePorNome() {
        String nome = "Vitor";
        ClienteDTO clienteDTO = new ClienteDTO().getToDTOConverter().apply(this.cliente);

        Response<ClienteDTO> response = new Response<>();
        response.setData(new ArrayList<ClienteDTO>() {{ add(clienteDTO);}});
        
        // Configurar o comportamento do mock da clienteFacade
        when(facade.buscarClientePorNome(nome)).thenReturn(ResponseEntity.ok(response));

        // Chamar o método da controller que você deseja testar
        ResponseEntity<Response<ClienteDTO>> responseEntity = controller.buscarClientePorNome(nome);

        // Verificar se o status de resposta é OK (200)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verificar se o corpo da resposta contém o clienteDTO esperado
        assertEquals(response.getData(), responseEntity.getBody().getData());

        // Verificar se o método do mock da clienteFacade foi chamado uma vez
        verify(facade, times(1)).buscarClientePorNome(nome);
    }
    
    @Test
    public void testBuscarClientePorRg() {
        String rg = "123456789";
        ClienteDTO clienteDTO = new ClienteDTO().getToDTOConverter().apply(this.cliente);

        Response<ClienteDTO> response = new Response<>();
        response.setData(new ArrayList<ClienteDTO>() {{ add(clienteDTO);}});
        
        // Configurar o comportamento do mock da clienteFacade
        when(facade.buscarClientePorRg(rg)).thenReturn(ResponseEntity.ok(response));

        // Chamar o método da controller que você deseja testar
        ResponseEntity<Response<ClienteDTO>> responseEntity = controller.buscarClientePorRg(rg);

        // Verificar se o status de resposta é OK (200)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verificar se o corpo da resposta contém o clienteDTO esperado
        assertEquals(response.getData(), responseEntity.getBody().getData());

        // Verificar se o método do mock da clienteFacade foi chamado uma vez
        verify(facade, times(1)).buscarClientePorRg(rg);
    }
}
