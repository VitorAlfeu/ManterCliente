package com.teste.mantercliente.api.facades;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

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
import com.teste.mantercliente.api.facades.impl.ClienteFacade;
import com.teste.mantercliente.api.response.Response;
import com.teste.mantercliente.api.services.IClienteService;

@ExtendWith(MockitoExtension.class)
public class ClienteFacadeUnitTest {

	@Spy
	@InjectMocks
	private ClienteFacade clienteFacade;
	
	@Mock
	private IClienteService clienteService;
	
	@Test
    public void testManterClienteClienteNovoComSucesso() {
        // Simule um clienteDTO com ID igual a 0, ou seja, um novo cliente
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("Nome do Cliente");
        clienteDTO.setRg("123456789");

        // Simule que o método findById retorna Optional.empty(), indicando que o cliente não existe
        when(clienteService.findById(clienteDTO.getId())).thenReturn(Optional.empty());

        // Simule que o método findByNomeAndRg retorna Optional.empty(), indicando que não há outro cliente com o mesmo nome e RG
        when(clienteService.findByNomeAndRg(clienteDTO.getNome(), clienteDTO.getRg())).thenReturn(Optional.empty());

        // Simule que a validação de campos gerais não tem erros
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        // Executando o método sob teste
        ClienteDTO retorno = clienteFacade.manterCliente(clienteDTO, result);

        // Verificando se o método de serviço foi chamado corretamente
        verify(clienteService).findById(clienteDTO.getId());
        verify(clienteService).findByNomeAndRg(clienteDTO.getNome(), clienteDTO.getRg());
        verify(clienteService).manterCliente(any(Cliente.class));

        // Verificando se o retorno não é nulo
        assertNotNull(retorno);
    }
	
    @Test
    public void testExcluirClienteClienteExistente() {
        long clienteId = 1L;

        // Simule que o método findById retorna um cliente existente
        Cliente cliente = new Cliente();
        when(clienteService.findById(clienteId)).thenReturn(Optional.of(cliente));

        // Executando o método sob teste
        ResponseEntity<String> response = clienteFacade.excluirCliente(clienteId);

        // Verificando se o método findById foi chamado
        verify(clienteService).findById(clienteId);


        // Verificando se a resposta é OK e contém a mensagem de sucesso
        ResponseEntity<String> responseEsperado = ResponseEntity.ok().body("Cliente excluído com sucesso!");
        assertEquals(responseEsperado.getBody(), response.getBody());
    }

    @Test
    public void testExcluirClienteClienteNaoExistente() {
        long clienteId = 2L;

        // Simule que o método findById retorna Optional.empty(), indicando que o cliente não existe
        when(clienteService.findById(clienteId)).thenReturn(Optional.empty());

        // Executando o método sob teste
        ResponseEntity<String> response = clienteFacade.excluirCliente(clienteId);

        // Verificando se o método findById foi chamado
        verify(clienteService).findById(clienteId);

        // Verificando se a resposta é BadRequest e contém a mensagem de erro
        assertTrue(response.getBody().contains("Nenhum Cliente encontrado com o Id: " + clienteId));
    }

    @Test
    public void testExcluirClienteExcecao() {
        long clienteId = 3L;

        // Simule que o método findById retorna um cliente existente
        Cliente cliente = new Cliente();
        when(clienteService.findById(clienteId)).thenReturn(Optional.of(cliente));

        // Simule que o método delete lança uma exceção
        doThrow(new RuntimeException("Erro ao excluir cliente")).when(clienteService).delete(Optional.ofNullable(cliente));

        // Executando o método sob teste
        ResponseEntity<String> response = clienteFacade.excluirCliente(clienteId);

        // Verificando se o método findById foi chamado
        verify(clienteService).findById(clienteId);

        // Verificando se o método delete foi chamado
        verify(clienteService).delete(Optional.ofNullable(cliente));

        // Verificando se a resposta é BadRequest e contém a mensagem de erro
        assertTrue(response.getBody().contains("Erro ao excluir Cliente"));
    }
    
    @Test
    public void testBuscarTodosClientes() {
        // Simule uma lista de clientes
        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes.add(new Cliente(1L, "Cliente 1", "123"));
        listaClientes.add(new Cliente(2L, "Cliente 2", "456"));
        listaClientes.add(new Cliente(3L, "Cliente 3", "789"));

        // Simule que o método findAll retorna a lista de clientes
        when(clienteService.findAll()).thenReturn(listaClientes);

        // Executando o método sob teste
        List<ClienteDTO> listaClienteDTO = clienteFacade.buscarTodosClientes();

        // Verificando se o método findAll foi chamado
        verify(clienteService).findAll();

        // Verificando se o número de elementos na lista de DTOs corresponde ao número de clientes simulados
        assertEquals(listaClientes.size(), listaClienteDTO.size());

        // Verificando se os nomes dos clientes correspondem nos DTOs
        IntStream.range(0, listaClientes.size())
        	.forEach(i -> assertEquals(listaClientes.get(i).getNome(), listaClienteDTO.get(i).getNome()));
    }
    
    @Test
    public void testBuscarTodosClientesJDBC() {
        // Simule uma lista de clientes
        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes.add(new Cliente(1L, "Cliente 1", "456"));
        listaClientes.add(new Cliente(2L, "Cliente 2", "789"));
        listaClientes.add(new Cliente(3L, "Cliente 3", "101112"));

        // Simule que o método findAllJDBC retorna a lista de clientes
        when(clienteService.findAllJDBC()).thenReturn(listaClientes);

        // Executando o método sob teste
        List<ClienteDTO> listaClienteDTO = clienteFacade.buscarTodosClientesJDBC();

        // Verificando se o método findAllJDBC foi chamado
        verify(clienteService).findAllJDBC();

        // Verificando se o número de elementos na lista de DTOs corresponde ao número de clientes simulados
        assertEquals(listaClientes.size(), listaClienteDTO.size());

        // Verificando se os nomes dos clientes correspondem nos DTOs
        IntStream.range(0, listaClientes.size())
        	.forEach(i -> assertEquals(listaClientes.get(i).getNome(), listaClienteDTO.get(i).getNome()));
    }
    
    @Test
    public void testBuscarClientePorIdClienteEncontrado() {
        String id = "1";
        long idLong = Long.parseLong(id);
        Cliente cliente = new Cliente(idLong, "Cliente 1", "123");

        // Simule que o método findById retorna um cliente
        when(clienteService.findById(idLong)).thenReturn(Optional.of(cliente));

        // Executando o método sob teste
        ResponseEntity<Response<ClienteDTO>> responseEntity = clienteFacade.buscarClientePorId(id);

        // Verificando se o método findById foi chamado
        verify(clienteService).findById(idLong);

        // Verificando se a resposta é OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verificando se a lista de DTOs contém o cliente encontrado
        assertTrue(responseEntity.getBody().getErrors().isEmpty());
        assertTrue(responseEntity.getBody().getData().size() == 1);
        assertEquals(cliente.getNome(), responseEntity.getBody().getData().get(0).getNome());
    }

    @Test
    public void testBuscarClientePorIdClienteNaoEncontrado() {
        String id = "2";
        long idLong = Long.parseLong(id);

        // Simule que o método findById não retorna um cliente (vazio)
        when(clienteService.findById(idLong)).thenReturn(Optional.empty());

        // Executando o método sob teste
        ResponseEntity<Response<ClienteDTO>> responseEntity = clienteFacade.buscarClientePorId(id);

        // Verificando se o método findById foi chamado
        verify(clienteService).findById(idLong);

        // Verificando se a lista de erros contém a mensagem apropriada
        assertFalse(responseEntity.getBody().getErrors().isEmpty());
        assertEquals("Nenhum Usuário encontrado com o Id: " + id, responseEntity.getBody().getErrors().get(0));
    }
    
    @Test
    public void testBuscarClientePorNomeClienteEncontrado() {
        String nome = "Cliente 1";
        Cliente cliente = new Cliente(1L, nome, "123");

        // Simule que o método findByNome retorna um cliente
        when(clienteService.findByNome(nome)).thenReturn(Optional.of(cliente));

        // Executando o método sob teste
        ResponseEntity<Response<ClienteDTO>> responseEntity = clienteFacade.buscarClientePorNome(nome);

        // Verificando se o método findByNome foi chamado
        verify(clienteService).findByNome(nome);

        // Verificando se a resposta é OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verificando se a lista de DTOs contém o cliente encontrado
        assertTrue(responseEntity.getBody().getErrors().isEmpty());
        assertTrue(responseEntity.getBody().getData().size() == 1);
        assertEquals(cliente.getNome(), responseEntity.getBody().getData().get(0).getNome());
    }

    @Test
    public void testBuscarClientePorNomeClienteNaoEncontrado() {
        String nome = "Cliente Inexistente";

        // Simule que o método findByNome não retorna um cliente (vazio)
        when(clienteService.findByNome(nome)).thenReturn(Optional.empty());

        // Executando o método sob teste
        ResponseEntity<Response<ClienteDTO>> responseEntity = clienteFacade.buscarClientePorNome(nome);

        // Verificando se o método findByNome foi chamado
        verify(clienteService).findByNome(nome);

        // Verificando se a lista de erros contém a mensagem apropriada
        assertFalse(responseEntity.getBody().getErrors().isEmpty());
        assertEquals("Nenhum Usuário encontrado com o Nome: " + nome, responseEntity.getBody().getErrors().get(0));
    }
    
    @Test
    public void testBuscarClientePorRgClienteEncontrado() {
        String rg = "123456789";
        Cliente cliente = new Cliente(1L, "Cliente 1", rg);

        // Simule que o método findByRg retorna um cliente
        when(clienteService.findByRg(rg)).thenReturn(Optional.of(cliente));

        // Executando o método sob teste
        ResponseEntity<Response<ClienteDTO>> responseEntity = clienteFacade.buscarClientePorRg(rg);

        // Verificando se o método findByRg foi chamado
        verify(clienteService).findByRg(rg);

        // Verificando se a resposta é OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verificando se a lista de DTOs contém o cliente encontrado
        assertTrue(responseEntity.getBody().getErrors().isEmpty());
        assertTrue(responseEntity.getBody().getData().size() == 1);
        assertEquals(cliente.getNome(), responseEntity.getBody().getData().get(0).getNome());
    }

    @Test
    public void testBuscarClientePorRgClienteNaoEncontrado() {
        String rg = "987654321";

        // Simule que o método findByRg não retorna um cliente (vazio)
        when(clienteService.findByRg(rg)).thenReturn(Optional.empty());

        // Executando o método sob teste
        ResponseEntity<Response<ClienteDTO>> responseEntity = clienteFacade.buscarClientePorRg(rg);

        // Verificando se o método findByRg foi chamado
        verify(clienteService).findByRg(rg);

        // Verificando se a lista de erros contém a mensagem apropriada
        assertFalse(responseEntity.getBody().getErrors().isEmpty());
        assertEquals("Nenhum Usuário encontrado com o Rg: " + rg, responseEntity.getBody().getErrors().get(0));
    }
}
