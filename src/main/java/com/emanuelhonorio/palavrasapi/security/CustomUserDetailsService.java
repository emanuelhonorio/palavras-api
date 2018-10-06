package com.emanuelhonorio.palavrasapi.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.emanuelhonorio.palavrasapi.model.Permissao;
import com.emanuelhonorio.palavrasapi.model.Usuario;
import com.emanuelhonorio.palavrasapi.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);
		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Username e/ou senha inválidos"));
		return new User(username, usuario.getSenha(), permissoes(usuario));
	}

	private Collection<? extends GrantedAuthority> permissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		for(Permissao permissao : usuario.getPesmissoes()) {
			authorities.add(new SimpleGrantedAuthority(permissao.getDescricao().toUpperCase()));
		}
		return authorities;
	}

}
