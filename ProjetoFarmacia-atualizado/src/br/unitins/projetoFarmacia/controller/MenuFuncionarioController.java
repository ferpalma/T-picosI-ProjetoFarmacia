package br.unitins.projetoFarmacia.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class MenuFuncionarioController implements Serializable {
	
	private static final long serialVersionUID = -3530838432521822223L;

	public String consultaCliente() {

		return "consultacliente.xhtml?faces-redirect=true";
	}

	public String consultaProduto() {

		return "consultaproduto.xhtml?faces-redirect=true";
	}

	public String telaLogin() {

		return "login.xhtml?faces-redirect=true";
	}
}
