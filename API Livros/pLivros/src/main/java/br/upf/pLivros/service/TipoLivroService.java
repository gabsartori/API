package br.upf.pLivros.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.upf.pLivros.dto.TipoLivroDTO;
import br.upf.pLivros.repository.TipoLivroRepository;

@Service
public class TipoLivroService {
	
	@Autowired
	private TipoLivroRepository tipoLivroRepository;
	
	public TipoLivroDTO salvar(TipoLivroDTO dto) {
		return tipoLivroRepository.save(dto);
	}
	
	public List<TipoLivroDTO> listarTodos() {
		return tipoLivroRepository.findAll();
	}
	
	public Optional<TipoLivroDTO> buscarPorId(Long id) {
		return tipoLivroRepository.findById(id);
	}
	
	public void removerPorId(Long id) {
		tipoLivroRepository.deleteById(id);
	}
	
  	public List<TipoLivroDTO> buscarPorLivroId(Long livroId) {
		return tipoLivroRepository.findByPorLivroId(livroId);
	}  
	

}













