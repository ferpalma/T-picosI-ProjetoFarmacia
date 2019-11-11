package br.unitins.projetoFarmacia.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.projetoFarmacia.DAO.UsuarioDAO;
import br.unitins.projetoFarmacia.application.Session;
import br.unitins.projetoFarmacia.application.Util;
import br.unitins.projetoFarmacia.model.Usuario;

@Named
@ViewScoped
public class LoginController implements Serializable{

	private static final long serialVersionUID = 1683313550131303264L;

	private Usuario usuario;

	
	@SuppressWarnings({ "static-access" })
	public String logar() {
		UsuarioDAO dao = new UsuarioDAO();
		String hashSenha = Util.hashSHA256(getUsuario().getSenha());
		Usuario usuario = 
			dao.login(getUsuario().getEmail(), hashSenha);
		
		if (usuario != null) {
			// armazenando um usuario na sessao
			
			if(usuario.getPerfil().equals(getUsuario().getPerfil().valueOf(1))) {
				Session.getInstance().setAttribute("usuarioLogado", usuario);
				return "menuadministrador.xhtml?faces-redirect=true";
			}
			if (usuario.getPerfil().equals(getUsuario().getPerfil().valueOf(2))){
				Session.getInstance().setAttribute("usuarioLogado", usuario);
				return "menufuncionario.xhtml?faces-redirect=true";	
			}else {
				Session.getInstance().setAttribute("usuarioLogado", usuario);
				return "menucliente.xhtml?faces-redirect=true";
			}
		}
		Util.addMessageError("Usuário ou Senha Inválido.");
		return null;
	}
	
	public void limpar() {
		//setUsuario(new Usuario());
		usuario = new Usuario();
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
