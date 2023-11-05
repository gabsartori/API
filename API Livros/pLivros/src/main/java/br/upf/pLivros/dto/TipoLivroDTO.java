package br.upf.pLivros.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "Tipo_Livro")
public class TipoLivroDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "tipo_livro", nullable = false)
	private String tipo_livro;
	
  /*	@ManyToOne
	@JoinColumn(name = "id_livro")
	private LivroDTO livro;  */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo_livro() {
		return tipo_livro;
	}

	public void setTipo_livro(String tipo_livro) {
		this.tipo_livro = tipo_livro;
	}

  /*	public LivroDTO getLivro() {
		return livro;
	}

	public void setLivro(LivroDTO livro) {
		this.livro = livro;
	}  */

	

}
