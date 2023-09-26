package com.teste.mantercliente.api.repositories.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.teste.mantercliente.api.entities.Cliente;
import com.teste.mantercliente.api.repositories.IClienteRepositoryJDBC;

@Repository
public class ClienteRepositoryJDBC implements IClienteRepositoryJDBC {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClienteRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Cliente> findAllJDBC() {
        String sql = "SELECT id, nome, rg FROM clientes";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Cliente pessoa = new Cliente();
            pessoa.setId(rs.getLong("id"));
            pessoa.setNome(rs.getString("nome"));
            pessoa.setRg(rs.getString("rg"));
            return pessoa;
        });
    }
	
}
