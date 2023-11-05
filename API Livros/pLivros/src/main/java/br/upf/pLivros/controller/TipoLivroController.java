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

import br.upf.pLivros.dto.TipoLivroDTO;
import br.upf.pLivros.service.TipoLivroService;
import br.upf.pLivros.utils.TokenJWT;

@RestController
@RequestMapping(value = "/plivros/tipoLivro")
public class TipoLivroController {
	@Autowired
	private TipoLivroService tipoLivroService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(value = "/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public TipoLivroDTO inserir(@RequestBody TipoLivroDTO tipoLivroDTO, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return tipoLivroService.salvar(tipoLivroDTO);
	}
	
	@GetMapping(value = "/listarTodos")
	@ResponseStatus(HttpStatus.OK)
	public List<TipoLivroDTO> listarTodos( 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return tipoLivroService.listarTodos();
	}
	
	@GetMapping(value = "/buscarPorId")
	@ResponseStatus(HttpStatus.OK)
	public TipoLivroDTO buscarPorId(@RequestHeader(value = "id") Long id, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return tipoLivroService.buscarPorId(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Tipo de livro não encontrado."));
	}
	
	@DeleteMapping(value = "/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerLivro(@RequestHeader(value = "id") Long id, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		tipoLivroService.buscarPorId(id).map(tipoLivro -> {
			tipoLivroService.removerPorId(tipoLivro.getId());
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, 
				"Tipo de livro não encontrado."));
	}	
	
	@PutMapping(value = "/atualizar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@RequestBody TipoLivroDTO userDTO, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		tipoLivroService.buscarPorId(userDTO.getId()).map(tipoLivroBase -> {
			modelMapper.map(userDTO, tipoLivroBase); 
			tipoLivroService.salvar(tipoLivroBase); 
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, 
				"Tipo de livro não encontrado."));
	}
	
	@GetMapping(value = "/buscarPorLivroId")
	@ResponseStatus(HttpStatus.OK)
	public List<TipoLivroDTO> buscarPorLivroId(@RequestHeader(value = "idLivro") Long idLivro, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return tipoLivroService.buscarPorLivroId(idLivro);
	}
}
