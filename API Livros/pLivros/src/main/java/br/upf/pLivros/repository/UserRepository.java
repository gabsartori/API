package br.upf.pLivros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.upf.pLivros.dto.UserDTO;

public interface UserRepository extends JpaRepository<UserDTO, Long>{

	public UserDTO findByEmail(String email);
	
  	@Query(nativeQuery = true, value = "select u.* from usuarios u, tipo_livro t, livros l "
									 + "where u.id = l.id_user "
									 + "and t.id = l.id_tipo "
									 + "and l.id = :idLivro "
									 + "order by u.nome_user asc")
	public List<UserDTO> findByPorLivroId(@Param("idLivro") Long idLivro);  
}
