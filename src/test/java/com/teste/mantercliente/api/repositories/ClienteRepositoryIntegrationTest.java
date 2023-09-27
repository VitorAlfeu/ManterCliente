package com.teste.mantercliente.api.repositories;
import com.teste.mantercliente.api.entities.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ClienteRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IClienteRepository clienteRepository;

    @Test
    public void testFindByNome() {
        // Crie um cliente de exemplo e persista no banco de dados usando o EntityManager
        Cliente cliente = new Cliente();
        cliente.setNome("João");
        cliente.setRg("12345");
        entityManager.persist(cliente);

        // Realize a consulta pelo nome
        Optional<Cliente> clienteEncontrado = clienteRepository.findByNome("João");

        // Verifique se o cliente foi encontrado
        assertTrue(clienteEncontrado.isPresent());
        assertEquals("João", clienteEncontrado.get().getNome());
    }

    @Test
    public void testFindByRg() {
        // Crie um cliente de exemplo e persista no banco de dados usando o EntityManager
        Cliente cliente = new Cliente();
        cliente.setNome("Maria");
        cliente.setRg("54321");
        entityManager.persist(cliente);

        // Realize a consulta pelo RG
        Optional<Cliente> clienteEncontrado = clienteRepository.findByRg("54321");

        // Verifique se o cliente foi encontrado
        assertTrue(clienteEncontrado.isPresent());
        assertEquals("54321", clienteEncontrado.get().getRg());
    }

    @Test
    public void testFindByNomeAndRg() {
        // Crie um cliente de exemplo e persista no banco de dados usando o EntityManager
        Cliente cliente = new Cliente();
        cliente.setNome("Pedro");
        cliente.setRg("98765");
        entityManager.persist(cliente);

        // Realize a consulta pelo nome e RG
        Optional<Cliente> clienteEncontrado = clienteRepository.findByNomeAndRg("Pedro", "98765");

        // Verifique se o cliente foi encontrado
        assertTrue(clienteEncontrado.isPresent());
        assertEquals("Pedro", clienteEncontrado.get().getNome());
        assertEquals("98765", clienteEncontrado.get().getRg());
    }
}
