package br.unitins.projetoFarmacia.controller;


import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.projetoFarmacia.DAO.DAO;
import br.unitins.projetoFarmacia.DAO.ProdutoDAO;
import br.unitins.projetoFarmacia.application.Util;
import br.unitins.projetoFarmacia.model.Produto;

@Named
@ViewScoped 
public class ProdutoController implements Serializable {

	private static final long serialVersionUID = 383861007954471732L;

	private Produto produto;
	
	public ProdutoController() {
		Flash flash = FacesContext.
				getCurrentInstance().
				getExternalContext().getFlash();
		flash.keep("produtoFlash");
		produto = (Produto) flash.get("produtoFlash");
	}
	
	public void incluir() {
		DAO<Produto> dao = new ProdutoDAO();
		// faz a inclusao no banco de dados
		try {
			dao.create(getProduto());
			dao.getConnection().commit();
			Util.addMessageInfo("Inclusão realizada com sucesso.");
			limpar();
		} catch (SQLException e) {
			dao.rollbackConnection();
			dao.closeConnection();
			Util.addMessageInfo("Erro ao incluir o Produto no Banco de Dados.");
			e.printStackTrace();
		}
	}
	
	public void alterar() {
		DAO<Produto> dao = new ProdutoDAO();
		// faz a alteracao no banco de dados
		try {
			dao.update(getProduto());
			dao.getConnection().commit();
			Util.addMessageInfo("Alteração realizada com sucesso.");
			limpar();
		} catch (SQLException e) {
			dao.rollbackConnection();
			dao.closeConnection();
			Util.addMessageInfo("Erro ao alterar o Usuário no Banco de Dados.");
			e.printStackTrace();
		}
	}
	
	
	public void excluir() {
		DAO<Produto> dao = new ProdutoDAO();
		// faz a exclusao no banco de dados
		try {
			dao.delete(getProduto().getIdProduto());
			dao.getConnection().commit();
			Util.addMessageInfo("Exclusão realizada com sucesso.");
			limpar();
		} catch (SQLException e) {
			dao.rollbackConnection();
			Util.addMessageInfo("Erro ao excluir o Produto no Banco de Dados.");
			e.printStackTrace();
		} finally {
			dao.closeConnection();
		}
	}


	public Produto getProduto() {
		if (produto == null) {
			produto = new Produto();
		}
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public void limpar() {
		produto = null;
	}
	
}