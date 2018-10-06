package com.emanuelhonorio.palavrasapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emanuelhonorio.palavrasapi.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	public Optional<Usuario> findByUsername(String username);
}
