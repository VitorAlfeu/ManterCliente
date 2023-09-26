package com.teste.mantercliente.api.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="clientes")
public class Cliente implements Serializable{

	private static final long serialVersionUID = -1835825622768953282L;
	
	private long id;
	private String nome;
	private String rg;
	
	public Cliente(long id, String nome, String rg) {
		super();
		this.id = id;
		this.nome = nome;
		this.rg = rg;
	}
	
	public Cliente() {
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "nome", nullable = false)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "rg", nullable = false)
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
}