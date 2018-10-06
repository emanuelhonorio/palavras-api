package com.emanuelhonorio.palavrasapi.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.emanuelhonorio.palavrasapi.model.enums.DificuldadeEnum;

@Entity
public class Palavra extends BaseModel {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String nome;

	@NotNull
	@Enumerated(EnumType.STRING)
	private DificuldadeEnum dificuldade;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	@Override
	public String toString() {
		return "Palavra [nome=" + nome + ", dificuldade=" + dificuldade + ", categoria=" + categoria + "]";
	}
	
	@PrePersist @PreUpdate
	private void prePersistUpdate() {
		this.nome = this.nome.toLowerCase();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public DificuldadeEnum getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(DificuldadeEnum dificuldade) {
		this.dificuldade = dificuldade;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
}
