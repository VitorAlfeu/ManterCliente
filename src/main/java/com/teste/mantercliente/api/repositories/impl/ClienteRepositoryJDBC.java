package com.teste.mantercliente.api.repositories.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.teste.mantercliente.api.entities.Cliente;
import com.teste.mantercliente.api.repositories.IClienteRepositoryJDBC;

/**
 * Implementação da interface IClienteRepositoryJDBC que fornece métodos para acessar dados de clientes
 * usando o JDBC (Java Database Connectivity).
 */
@Repository
public class ClienteRepositoryJDBC implements IClienteRepositoryJDBC {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClienteRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Recupera todos os clientes do banco de dados usando uma consulta SQL simples.
     *
     * @return Uma lista de objetos Cliente contendo os dados dos clientes encontrados.
     */
    public List<Cliente> findAllJDBC() {
        // Define a consulta SQL para recuperar todos os clientes do banco de dados.
        String sql = "SELECT id, nome, rg FROM clientes";

        // Executa a consulta SQL usando o jdbcTemplate e mapeia os resultados para objetos Cliente.
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Cliente cliente = new Cliente();
            cliente.setId(rs.getLong("id"));
            cliente.setNome(rs.getString("nome"));
            cliente.setRg(rs.getString("rg"));
            return cliente;
        });
    }
}
