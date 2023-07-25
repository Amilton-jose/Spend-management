package com.amilton.erp.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.amilton.erp.model.Gasto;

public class Gastos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public Gastos() {

	}

	public Gastos(EntityManager manager) {
		this.manager = manager;
	}

	public Gasto porId(Long id) {
		return manager.find(Gasto.class, id);
	}

	public List<Gasto> pesquisar(String descricao) {
		String jpql = "from Gasto where descricao like :descricao";
		
		TypedQuery<Gasto> query = manager.createQuery(jpql, Gasto.class);

		query.setParameter("descricao", descricao + "%");

		return query.getResultList();
	}
	
	public List<Gasto> todos() {
		return manager.createQuery("from Gasto", Gasto.class).getResultList();
	}

	public Gasto guardar(Gasto gasto) {
		return manager.merge(gasto);
	}

	public void remover(Gasto gasto) {
		gasto = porId(gasto.getId());
		manager.remove(gasto);
	}

}
