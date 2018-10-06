package com.emanuelhonorio.palavrasapi.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emanuelhonorio.palavrasapi.error.exception.EntityNotValidException;
import com.emanuelhonorio.palavrasapi.model.Categoria;
import com.emanuelhonorio.palavrasapi.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscarPeloId(Long id) {
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
		
		if(!categoriaOptional.isPresent()) {
			throw new EntityNotFoundException();
		}
		return categoriaOptional.get();
	}

	public Categoria atualizar(Categoria categoria, Long id) {
		Categoria categoriaSalva = buscarPeloId(id);
		validarAtributosIguaisInvalidos(categoria);
		BeanUtils.copyProperties(categoria, categoriaSalva, "id"); // except id
		return categoriaRepository.save(categoriaSalva);
	}
	
	public Categoria salvar(Categoria categoria) {
		categoria.setId(null);
		validarAtributosIguaisInvalidos(categoria);
		
		return categoriaRepository.save(categoria);
	}

	private void validarAtributosIguaisInvalidos(Categoria categoria) {
		Optional<Categoria> categoriaOptional = categoriaRepository.findByNome(categoria.getNome());
		if(categoriaOptional.isPresent())
			throw new EntityNotValidException("Já existe uma categoria com esse nome"); // TODO: mudar
	}
}
