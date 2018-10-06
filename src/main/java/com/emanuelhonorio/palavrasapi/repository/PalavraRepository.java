package com.emanuelhonorio.palavrasapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emanuelhonorio.palavrasapi.model.Palavra;

public interface PalavraRepository extends JpaRepository<Palavra, Long>{
	public Optional<Palavra> findByNome(String nome);
}
