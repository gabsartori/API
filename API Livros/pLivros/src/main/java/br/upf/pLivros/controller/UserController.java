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

import br.upf.pLivros.dto.UserDTO;
import br.upf.pLivros.service.UserService;
import br.upf.pLivros.utils.TokenJWT;

@RestController
@RequestMapping(value = "/plivros/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(value = "/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO inserir(@RequestBody UserDTO userDTO, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return userService.salvar(userDTO);
	}
	
	@GetMapping(value = "/listarTodos")
	@ResponseStatus(HttpStatus.OK)
	public List<UserDTO> listarTodos(
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return userService.listarTodos();
	}
	
	@GetMapping(value = "/buscarPorId")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO buscarPorId(@RequestHeader(value = "id") Long id, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return userService.buscarPorId(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Usuário não encontrado."));
	}
	
	@DeleteMapping(value = "/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerLivro(@RequestHeader(value = "id") Long id, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		userService.buscarPorId(id).map(user -> {
			userService.removerPorId(user.getId());
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, 
				"Livro não encontrado."));
	}	
	
	@PutMapping(value = "/atualizar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@RequestBody UserDTO userDTO, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		userService.buscarPorId(userDTO.getId()).map(userBase -> {
			modelMapper.map(userDTO, userBase); 
			userService.salvar(userBase); 
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, 
				"Usuário não encontrado."));
	}
	
	@GetMapping(value = "/buscarPorLivroId")
	@ResponseStatus(HttpStatus.OK)
	public List<UserDTO> buscarPorLivroId(@RequestHeader(value = "idLivro") Long idLivro, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return userService.buscarPorLivroId(idLivro);
	}
	
	@GetMapping(value = "/buscarPorEmail")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO findByEmail(@RequestHeader(value = "email") String email, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return userService.buscarPorEmail(email);
	}
	
	@GetMapping(value = "/authorize")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO authorize(@RequestHeader(value = "email") String email,
			@RequestHeader(value = "password") String password) {
		UserDTO userDTO;
		
		if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
			userDTO = userService.buscarPorEmail(email);
			
			if (userDTO.getId() != null) {
				if (userDTO.getSenha().equals(password)) {
					userDTO.setToken(TokenJWT.processarTokenJWT(email));
					return userDTO;
				} else {
					return null;
				}
			}else {
				return null;
			}
		}else {
			return null;
		}
	}
}
