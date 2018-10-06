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

import com.emanuelhonorio.palavrasapi.model.Categoria;
import com.emanuelhonorio.palavrasapi.repository.CategoriaRepository;
import com.emanuelhonorio.palavrasapi.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Categoria buscarPeloId(@PathVariable Long id) {
		return categoriaService.buscarPeloId(id);
	}
	
	@PreAuthorize("hasAuthority('ROLE_WRITE_CATEGORIA')")
	@PostMapping
	public Categoria salvar(@RequestBody @Valid Categoria categoria) {
		return categoriaService.salvar(categoria);
	}
	
	@PreAuthorize("hasAuthority('ROLE_WRITE_CATEGORIA')")
	@PutMapping("/{id}")
	public Categoria atualizar(@RequestBody @Valid Categoria categoria, @PathVariable Long id) {
		return categoriaService.atualizar(categoria, id);
	}
	
	@PreAuthorize("hasAuthority('ROLE_WRITE_CATEGORIA')")
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		categoriaRepository.deleteById(id);
	}
	
}
