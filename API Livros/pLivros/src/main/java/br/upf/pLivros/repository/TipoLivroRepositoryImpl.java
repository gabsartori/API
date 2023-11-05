package br.upf.pLivros.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public abstract class TipoLivroRepositoryImpl implements TipoLivroRepository {

	@PersistenceContext
	private EntityManager em;
}
