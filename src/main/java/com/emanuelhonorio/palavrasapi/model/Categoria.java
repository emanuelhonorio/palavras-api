package com.emanuelhonorio.palavrasapi.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Categoria extends BaseModel {
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String nome;

	@JsonIgnore
	@OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Palavra> palavras;

	@Override
	public String toString() {
		return "Categoria [nome=" + nome + "]";
	}

	@PrePersist
	@PreUpdate
	private void prePersistUpdate() {
		this.nome = this.nome.toLowerCase();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Palavra> getPalavras() {
		return palavras;
	}

	public void setPalavras(List<Palavra> palavras) {
		this.palavras = palavras;
	}

}
