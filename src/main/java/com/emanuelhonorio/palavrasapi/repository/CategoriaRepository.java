package com.emanuelhonorio.palavrasapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emanuelhonorio.palavrasapi.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	public Optional<Categoria> findByNome(String nome);
}
