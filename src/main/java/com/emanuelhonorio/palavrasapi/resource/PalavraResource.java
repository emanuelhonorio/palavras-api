package com.emanuelhonorio.palavrasapi.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emanuelhonorio.palavrasapi.model.Palavra;
import com.emanuelhonorio.palavrasapi.repository.PalavraRepository;
import com.emanuelhonorio.palavrasapi.service.PalavraService;

@RestController
@RequestMapping("/palavras")
public class PalavraResource {
	
	@Autowired
	private PalavraRepository palavraRepository;
	
	@Autowired
	private PalavraService palavraService;
	
	@GetMapping
	public List<Palavra> list() {
		return palavraRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Palavra buscarPeloId(@PathVariable Long id) {
		return palavraService.buscarPeloId(id);
	}
	
	@PreAuthorize("hasAuthority('ROLE_WRITE_PALAVRA')")
	@PostMapping
	public Palavra save(@RequestBody @Valid Palavra palavra) {
		Palavra palavraSalva = palavraService.salvar(palavra);
		return palavraSalva;
	}
	
	@PreAuthorize("hasAuthority('ROLE_WRITE_PALAVRA')")
	@PutMapping("/{id}")
	public Palavra atualizar(@RequestBody @Valid Palavra palavra, @PathVariable Long id) {
		return palavraService.atualizar(palavra, id);
	}
	
	@PreAuthorize("hasAuthority('ROLE_WRITE_PALAVRA')")
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		palavraRepository.deleteById(id);
	}

}
