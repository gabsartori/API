package br.upf.pLivros.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public abstract class LivroRepositoryImpl implements LivroRepository {
	@PersistenceContext
	private EntityManager em;
	

}
