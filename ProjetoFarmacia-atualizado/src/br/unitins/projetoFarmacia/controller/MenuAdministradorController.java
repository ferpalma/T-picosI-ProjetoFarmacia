package br.unitins.projetoFarmacia.controller;


import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class MenuAdministradorController implements Serializable{

	private static final long serialVersionUID = -721231050396675132L;

	public String consultaCliente() {

		return "consultacliente.xhtml?faces-redirect=true";
	}

	public String consultaProduto() {

		return "consultaproduto.xhtml?faces-redirect=true";
	}
	
	public String cadastraUsuario() {

		return "usuario.xhtml?faces-redirect=true";
	}

	public String cadastroProduto() {

		return "produto.xhtml?faces-redirect=true";
	}
	
	public String vendaProduto() {

		return "venda.xhtml?faces-redirect=true";
	}
	
	public String historico() {

		return "historico.xhtml?faces-redirect=true";
	}

	public String telaLogin() {

		return "login.xhtml?faces-redirect=true";
	}
}
