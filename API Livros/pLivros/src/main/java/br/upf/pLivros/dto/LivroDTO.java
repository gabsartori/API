package br.upf.pLivros.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "Livros")
public class LivroDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome_livro", nullable = false)
	private String nome_livro;
	
	@Column(name = "autor", nullable = false)
	private String autor;
	
	@ManyToOne
	@JoinColumn(name = "id_user")
	private UserDTO user;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo")
	private TipoLivroDTO tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome_livro() {
		return nome_livro;
	}

	public void setNome_livro(String nome_livro) {
		this.nome_livro = nome_livro;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public UserDTO getUser() {
    	return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}  
	
	public TipoLivroDTO getTipo() {
    	return tipo;
	}

	public void setTipo(TipoLivroDTO tipo) {
		this.tipo = tipo;
	}  

}
