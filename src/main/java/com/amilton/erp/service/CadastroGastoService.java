package com.amilton.erp.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.amilton.erp.model.Gasto;
import com.amilton.erp.repository.Gastos;
import com.amilton.erp.util.Transacional;

public class CadastroGastoService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Gastos gastos;
	
	@Transacional
	public void salvar(Gasto gasto) {
		gastos.guardar(gasto);
	}
	
	@Transacional
	public void excluir(Gasto gasto) {
		gastos.remover(gasto);
	}
}
