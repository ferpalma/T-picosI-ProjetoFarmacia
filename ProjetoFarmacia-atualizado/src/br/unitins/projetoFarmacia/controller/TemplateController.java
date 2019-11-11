package br.unitins.projetoFarmacia.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.projetoFarmacia.application.Session;
import br.unitins.projetoFarmacia.model.Usuario;

@Named
@ViewScoped
public class TemplateController implements Serializable {

	private static final long serialVersionUID = -2168638372168792780L;

	private Usuario usuarioLogado;
	
	public Usuario getUsuarioLogado() {
		if (usuarioLogado == null) {
			usuarioLogado = (Usuario) Session.getInstance().getAttribute("usuarioLogado");
			if(usuarioLogado == null)
				usuarioLogado = new Usuario();
		}
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public String encerrarSessao() {
		Session.getInstance().invalidateSession();
		return "login.xhtml?faces-redirect=true";
	}
	
	public String login() {
		
		return "login.xhtml?faces-redirect=true";
	}
	
	public String cadastroUsuario() {
		
		return "usuario.xhtml?faces-redirect=true";
	}
	
	public String cadastroProduto() {
		
		return "produto.xhtml?faces-redirect=true";
	}
	
	public String vendaProduto() {
		
		return "vendaProduto.xhtml?faces-redirect=true";
	}
	
	public String carrinho() {
		
		return "carrinho.xhtml?faces-redirect=true";
	}
	
	public String historicoVenda() {
		
		return "historicoVenda.xhtml?faces-redirect=true";
	}
}
