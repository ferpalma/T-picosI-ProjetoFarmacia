package br.unitins.projetoFarmacia.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class MenuClienteController implements Serializable{

	private static final long serialVersionUID = -3517037539749822256L;

	public String cadastroUsuario() {

		return "usuario.xhtml?faces-redirect=true";
	}

	public String consultaProduto() {

		return "consultaproduto.xhtml?faces-redirect=true";
	}

	public String carrinho() {

		return "carrinho.xhtml?faces-redirect=true";
	}
	
	public String historico() {

		return "historico.xhtml?faces-redirect=true";
	}

	public String sair() {

		return "login.xhtml?faces-redirect=true";
	}
	
}
