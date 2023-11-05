package br.upf.pLivros.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.upf.pLivros.dto.LivroDTO;
import br.upf.pLivros.service.LivroService;
import br.upf.pLivros.utils.TokenJWT;

@RestController
@RequestMapping(value = "/plivros/livro")
public class LivroController {
	@Autowired
	private LivroService livroService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(value = "/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public LivroDTO inserir(@RequestBody LivroDTO livroDTO, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return livroService.salvar(livroDTO);
	}
	
	@GetMapping(value = "/listarTodos")
	@ResponseStatus(HttpStatus.OK)
	public List<LivroDTO> listarTodos( 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return livroService.listarTodos();
	}
	
	@GetMapping(value = "/buscarPorId")
	@ResponseStatus(HttpStatus.OK)
	public LivroDTO buscarPorId(@RequestHeader(value = "id") Long id, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return livroService.buscarPorId(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Livro não encontrado."));
	}
	
	@DeleteMapping(value = "/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerLivro(@RequestHeader(value = "id") Long id, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		livroService.buscarPorId(id).map(livro -> {
			livroService.removerPorId(livro.getId());
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, 
				"Livro não encontrado."));
	}	
	
	@PutMapping(value = "/atualizar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@RequestBody LivroDTO livroDTO, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		livroService.buscarPorId(livroDTO.getId()).map(livroBase -> {
			modelMapper.map(livroDTO, livroBase); 
			livroService.salvar(livroBase); 
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, 
				"Livro não encontrado."));
	}
}












