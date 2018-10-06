package com.emanuelhonorio.palavrasapi.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emanuelhonorio.palavrasapi.error.exception.EntityNotValidException;
import com.emanuelhonorio.palavrasapi.model.Palavra;
import com.emanuelhonorio.palavrasapi.repository.PalavraRepository;

@Service
public class PalavraService {

	@Autowired
	PalavraRepository palavraRepository;

	public Palavra salvar(Palavra palavra) {
		palavra.setId(null);
		validarAtributosIguaisInvalidos(palavra);
		return palavraRepository.save(palavra);
	}
	
	public Palavra atualizar(Palavra palavra, Long id) {
		Palavra pessoaSalva = buscarPeloId(id);
		validarAtributosIguaisInvalidos(palavra);
		BeanUtils.copyProperties(palavra, pessoaSalva, "id");
		return palavraRepository.save(pessoaSalva);
	}

	
	public Palavra buscarPeloId(Long id) {
		Optional<Palavra> palavraOptional = palavraRepository.findById(id);
		if(!palavraOptional.isPresent()) {
			throw new EntityNotFoundException(Palavra.class.getSimpleName()+" not found with id " + id);
		}
		return palavraOptional.get();
	}

	private void validarAtributosIguaisInvalidos(Palavra palavra) {
		Optional<Palavra> palavraComMesmoNome = palavraRepository.findByNome(palavra.getNome());
		if(palavraComMesmoNome.isPresent())
			throw new EntityNotValidException("Já existe uma palavra com esse nome"); // TODO: mudar
	}

}

