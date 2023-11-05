package br.upf.pLivros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.upf.pLivros.dto.LivroDTO;

public interface LivroRepository extends JpaRepository<LivroDTO, Long> {
	
	@Query(nativeQuery = true, value = "select l.* from usuarios u, tipo_livro t, livros l "
								     + "where u.id = l.id_user "
								     + "and t.id = l.id_tipo "
								     + "and l.id = :idLivro "
								     + "order by l.nome_livro asc")
public List<LivroDTO> findByPorLivroId(@Param("idLivro") Long idLivro);

}
