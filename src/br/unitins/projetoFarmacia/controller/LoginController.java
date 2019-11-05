package br.unitins.projetoFarmacia.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.projetoFarmacia.DAO.UsuarioDAO;
import br.unitins.projetoFarmacia.application.Util;
import br.unitins.projetoFarmacia.model.Usuario;

@Named
@ViewScoped
public class LoginController implements Serializable{

	private static final long serialVersionUID = 1683313550131303264L;

	private Usuario usuario;

	
	public String logar() {
		UsuarioDAO dao = new UsuarioDAO();
		String hashSenha = Util.hashSHA256(getUsuario().getSenha());
		Usuario usuario = 
			dao.login(getUsuario().getEmail(), hashSenha);
		
		if (usuario != null) {
			return "usuario.xhtml?faces-redirect=true";
		}
		Util.addMessageError("Usuário ou Senha Inválido.");
		return null;
	}
	
	public void limpar() {
		setUsuario(new Usuario());
//		usuario = new Usuario();
	}

	public Usuario getUsuario() {
		if (usuario == null)
			usuario = new Usuario();
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
