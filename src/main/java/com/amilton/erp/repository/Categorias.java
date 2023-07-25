package com.amilton.erp.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.amilton.erp.model.Categoria;

public class Categorias implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public Categorias() {

	}

	public Categorias(EntityManager manager) {
		this.manager = manager;
	}

	public Categoria porId(Long id) {
		return manager.find(Categoria.class, id);
	}

	public List<Categoria> pesquisar(String nome) {

		TypedQuery<Categoria> query = manager.createQuery(" from Categoria where nome like :nome", Categoria.class);

		query.setParameter("nome", nome + "%");

		return query.getResultList();
	}

	public Categoria guardar(Categoria categoria) {
		return manager.merge(categoria);
	}

	public void remover(Categoria categoria) {
		categoria = porId(categoria.getId());
		manager.merge(categoria);
	}

}
