package com.teste.mantercliente.api.dtos;

import java.util.Objects;
import java.util.function.Function;

import com.teste.mantercliente.api.entities.Cliente;

public class ClienteDTO {

	private long id;
	private String nome;
	private String rg;
	
	public ClienteDTO(long id, String nome, String rg) {
		super();
		this.id = id;
		this.nome = nome;
		this.rg = rg;
	}
	
	public ClienteDTO() {
		
	}
	
	public static ToDTO getToDTOConverter() {
		return new ToDTO();
	}
	
	public static class ToDTO implements Function<Cliente, ClienteDTO>{
		
		public ClienteDTO apply(Cliente entity) {
			ClienteDTO dto = new ClienteDTO();
			
			if(Objects.nonNull(entity)) {
				dto.id = entity.getId();
				dto.nome = entity.getNome();
				dto.rg = entity.getRg();
			}
			
			return dto;
		}
		
	}
	
	public static ToEntity getToEntityConverter() {
		return new ToEntity();
	}
	
	public static class ToEntity implements Function<ClienteDTO, Cliente>{
		
		public Cliente apply(ClienteDTO dto) {
			Cliente entity = new Cliente();
			
			if(Objects.nonNull(dto)) {
				entity.setId(dto.getId());
				entity.setNome(dto.getNome());
				entity.setRg(dto.getRg());
			}
			
			return entity;
		}
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
}
