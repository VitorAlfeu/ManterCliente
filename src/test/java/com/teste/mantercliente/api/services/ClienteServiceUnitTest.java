package com.teste.mantercliente.api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.teste.mantercliente.api.entities.Cliente;
import com.teste.mantercliente.api.repositories.IClienteRepository;
import com.teste.mantercliente.api.repositories.IClienteRepositoryJDBC;
import com.teste.mantercliente.api.services.impl.ClienteService;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceUnitTest {

	@Spy
	@InjectMocks
	private ClienteService clienteService;
	
	@Mock
	private IClienteRepository clienteRepository;
	
	@Mock
	private IClienteRepositoryJDBC clienteRepositoryJDBC;
	
    @Test
    public void testManterCliente() {
        Cliente cliente = new Cliente(1L, "Nome do Cliente", "123456789");

        // Simulando que o método save retorna o cliente salvo
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Executando o método sob teste
        Cliente resultado = clienteService.manterCliente(cliente);

        // Verificando se o método save foi chamado
        verify(clienteRepository).save(cliente);

        // Verificando se o resultado é igual ao cliente original
        assertNotNull(resultado);
        assertEquals(cliente.getId(), resultado.getId());
        assertEquals(cliente.getNome(), resultado.getNome());
        assertEquals(cliente.getRg(), resultado.getRg());
    }
    
    @Test
    public void testFindByIdClienteEncontrado() {
        long id = 1L;
        Cliente cliente = new Cliente(id, "Nome do Cliente", "123456789");

        // Simulando que o método findById retorna um cliente
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        // Executando o método sob teste
        Optional<Cliente> resultado = clienteService.findById(id);

        // Verificando se o método findById foi chamado
        verify(clienteRepository).findById(id);

        // Verificando se o resultado é um Optional contendo o cliente
        assertTrue(resultado.isPresent());
        assertEquals(cliente, resultado.get());
    }

    @Test
    public void testFindByIdClienteNaoEncontrado() {
        long id = 2L;

        // Simulando que o método findById não retorna um cliente (vazio)
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        // Executando o método sob teste
        Optional<Cliente> resultado = clienteService.findById(id);

        // Verificando se o método findById foi chamado
        verify(clienteRepository).findById(id);

        // Verificando se o resultado é um Optional vazio
        assertFalse(resultado.isPresent());
    }
    
    @Test
    public void testFindByNomeClienteEncontrado() {
        String nome = "Nome do Cliente";
        Cliente cliente = new Cliente(1L, nome, "123456789");

        // Simulando que o método findByNome retorna um cliente
        when(clienteRepository.findByNome(nome)).thenReturn(Optional.of(cliente));

        // Executando o método sob teste
        Optional<Cliente> resultado = clienteService.findByNome(nome);

        // Verificando se o método findByNome foi chamado
        verify(clienteRepository).findByNome(nome);

        // Verificando se o resultado é um Optional contendo o cliente
        assertTrue(resultado.isPresent());
        assertEquals(cliente, resultado.get());
    }

    @Test
    public void testFindByNomeClienteNaoEncontrado() {
        String nome = "Nome do Cliente";

        // Simulando que o método findByNome não retorna um cliente (vazio)
        when(clienteRepository.findByNome(nome)).thenReturn(Optional.empty());

        // Executando o método sob teste
        Optional<Cliente> resultado = clienteService.findByNome(nome);

        // Verificando se o método findByNome foi chamado
        verify(clienteRepository).findByNome(nome);

        // Verificando se o resultado é um Optional vazio
        assertFalse(resultado.isPresent());
    }
    
    @Test
    public void testFindByRgClienteEncontrado() {
        String rg = "123456789";
        Cliente cliente = new Cliente(1L, "Nome do Cliente", rg);

        // Simulando que o método findByRg retorna um cliente
        when(clienteRepository.findByRg(rg)).thenReturn(Optional.of(cliente));

        // Executando o método sob teste
        Optional<Cliente> resultado = clienteService.findByRg(rg);

        // Verificando se o método findByRg foi chamado
        verify(clienteRepository).findByRg(rg);

        // Verificando se o resultado é um Optional contendo o cliente
        assertTrue(resultado.isPresent());
        assertEquals(cliente, resultado.get());
    }

    @Test
    public void testFindByRgClienteNaoEncontrado() {
        String rg = "987654321";

        // Simulando que o método findByRg não retorna um cliente (vazio)
        when(clienteRepository.findByRg(rg)).thenReturn(Optional.empty());

        // Executando o método sob teste
        Optional<Cliente> resultado = clienteService.findByRg(rg);

        // Verificando se o método findByRg foi chamado
        verify(clienteRepository).findByRg(rg);

        // Verificando se o resultado é um Optional vazio
        assertFalse(resultado.isPresent());
    }
    
    @Test
    public void testFindByNomeAndRgClienteEncontrado() {
        String nome = "Nome do Cliente";
        String rg = "123456789";
        Cliente cliente = new Cliente(1L, nome, rg);

        // Simulando que o método findByNomeAndRg retorna um cliente
        when(clienteRepository.findByNomeAndRg(nome, rg)).thenReturn(Optional.of(cliente));

        // Executando o método sob teste
        Optional<Cliente> resultado = clienteService.findByNomeAndRg(nome, rg);

        // Verificando se o método findByNomeAndRg foi chamado
        verify(clienteRepository).findByNomeAndRg(nome, rg);

        // Verificando se o resultado é um Optional contendo o cliente
        assertTrue(resultado.isPresent());
        assertEquals(cliente, resultado.get());
    }

    @Test
    public void testFindByNomeAndRgClienteNaoEncontrado() {
        String nome = "Nome do Cliente";
        String rg = "987654321";

        // Simulando que o método findByNomeAndRg não retorna um cliente (vazio)
        when(clienteRepository.findByNomeAndRg(nome, rg)).thenReturn(Optional.empty());

        // Executando o método sob teste
        Optional<Cliente> resultado = clienteService.findByNomeAndRg(nome, rg);

        // Verificando se o método findByNomeAndRg foi chamado
        verify(clienteRepository).findByNomeAndRg(nome, rg);

        // Verificando se o resultado é um Optional vazio
        assertFalse(resultado.isPresent());
    }
}
