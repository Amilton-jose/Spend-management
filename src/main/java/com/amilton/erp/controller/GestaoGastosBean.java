package com.amilton.erp.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.amilton.erp.model.Categoria;
import com.amilton.erp.model.Gasto;
import com.amilton.erp.repository.Categorias;
import com.amilton.erp.repository.Gastos;
import com.amilton.erp.service.CadastroGastoService;
import com.amilton.erp.util.FacesMessages;

@Named
@ViewScoped
public class GestaoGastosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Gastos gastos;

	@Inject
	private Categorias categorias;

	@Inject
	private CadastroGastoService cadastroGastoService;

	@Inject
	private FacesMessages messages;

	private List<Gasto> listaGastos;

	private String termoPesquisa;

	private Converter categoriaConverter;

	private Gasto gasto;

	private Map<String, BigDecimal> totalPorCategoria;

	private BigDecimal totalGeral;

	private BarChartModel barChartModel = new BarChartModel();

	@PostConstruct
	public void init() {
		todosGastos();
		montarGrafico();
	}

	public void montarGrafico() {
		barChartModel = new BarChartModel();
		totalPorCategoria = new HashMap<>();

		for (Gasto gasto : listaGastos) {
			String categoria = gasto.getCategoria().getNome();
			BigDecimal valorGasto = gasto.getValor();

			totalPorCategoria.merge(categoria, valorGasto, BigDecimal::add);
		}

		ChartSeries serieCategoria = new ChartSeries();
		for (String categoria : totalPorCategoria.keySet()) {
			BigDecimal totalCategoria = totalPorCategoria.get(categoria);
			serieCategoria.set(categoria, totalCategoria);
		}

		barChartModel.addSeries(serieCategoria);
		barChartModel.setTitle("Gráfico de Gastos por Categoria");
	}

	public BigDecimal calcularTotalDeGastos() {
		totalGeral = listaGastos.stream().map(Gasto::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
		return totalGeral;
	}

	public void totalDeGastosPorCategoria() {
		totalPorCategoria = new HashMap<>();

		for (Gasto gasto : listaGastos) {
			String categoria = gasto.getCategoria().getNome();
			BigDecimal valorGasto = gasto.getValor();

			totalPorCategoria.merge(categoria, valorGasto, BigDecimal::add);
		}

	}

	public Map<String, BigDecimal> getTotalPorCategoria() {
		return totalPorCategoria;
	}

	public void prepararNovoGasto() {
		gasto = new Gasto();
	}

	public void prepararEdicao() {
		categoriaConverter = new CategoriaConverter(Arrays.asList(gasto.getCategoria()));
	}

	public void excluir() {
		cadastroGastoService.excluir(gasto);
		gasto = null;
		atualizarRegistros();
		montarGrafico();
		messages.info("Gasto exluído com sucesso!");
	}

	public void salvar() {
		cadastroGastoService.salvar(gasto);
		atualizarRegistros();
		montarGrafico();
		messages.info("Gasto salvo com sucesso!");
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:gastosDataTable", "frm:messages"));
	}

	private void atualizarRegistros() {

		if (jaHouvePesquisa()) {
			pesquisar();
		} else {
			todosGastos();
		}

	}

	public void pesquisar() {
		listaGastos = gastos.pesquisar(termoPesquisa);
		if (listaGastos.isEmpty()) {
			messages.info("Sua consulta não retornou registros.");
		}
	}

	public void todosGastos() {
		listaGastos = gastos.todos();
	}

	public List<Categoria> completarCategoria(String termo) {
		List<Categoria> listaCategorias = categorias.pesquisar(termo);

		categoriaConverter = new CategoriaConverter(listaCategorias);

		return listaCategorias;
	}

	private boolean jaHouvePesquisa() {
		return termoPesquisa != null && !"".equals(termoPesquisa);
	}

	public Gasto getGasto() {
		return gasto;
	}

	public void setGasto(Gasto gasto) {
		this.gasto = gasto;
	}

	public Converter getCategoriaConverter() {
		return categoriaConverter;
	}

	public List<Gasto> getListaGastos() {
		return listaGastos;
	}

	public String getTermoPesquisa() {
		return termoPesquisa;
	}

	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}

	public boolean isGastoSelecionado() {
		return gasto != null && gasto.getId() != null;
	}

	public BarChartModel getBarChartModel() {
		return barChartModel;
	}

	public void setBarChartModel(BarChartModel barChartModel) {
		this.barChartModel = barChartModel;
	}

}
