package com.teste.mantercliente.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.teste.mantercliente.api.entities.Cliente;

/**
 * Esta interface define os métodos de consulta para a entidade Cliente utilizando Spring Data JPA.
 */
public interface IClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Busca um cliente pelo nome.
     *
     * @param nome O nome do cliente a ser pesquisado.
     * @return Um objeto Optional que pode conter o cliente encontrado, ou vazio se não encontrado.
     */
    Optional<Cliente> findByNome(String nome);

    /**
     * Executa uma query nativa para buscar um cliente pelo RG.
     *
     * @param rg O RG do cliente a ser pesquisado.
     * @return Um objeto Optional que pode conter o cliente encontrado, ou vazio se não encontrado.
     */
    @Query(value = "SELECT * FROM clientes WHERE rg = ?1%", nativeQuery = true)
    Optional<Cliente> findByRg(String rg);

    /**
     * Busca um cliente pelo nome e RG.
     *
     * @param nome O nome do cliente a ser pesquisado.
     * @param rg   O RG do cliente a ser pesquisado.
     * @return Um objeto Optional que pode conter o cliente encontrado, ou vazio se não encontrado.
     */
    Optional<Cliente> findByNomeAndRg(String nome, String rg);
}
