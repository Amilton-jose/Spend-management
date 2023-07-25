package com.amilton.erp.controller;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.amilton.erp.model.Categoria;

public class CategoriaConverter implements Converter {
	
	private List<Categoria> listaCategorias;
	
	public CategoriaConverter(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(value == null) {
			return null;
		}
		
		Long id = Long.valueOf(value);
		
		for (Categoria categoria : listaCategorias) {
			if(id.equals(categoria.getId())){
				return categoria;
			}
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value == null) {
			return null;
		}
		
		Categoria categoria = (Categoria) value;
		
		return categoria.getId().toString();
	}

}
