package br.unitins.projetoFarmacia.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.projetoFarmacia.DAO.DAO;
import br.unitins.projetoFarmacia.DAO.ProdutoDAO;
import br.unitins.projetoFarmacia.application.Session;
import br.unitins.projetoFarmacia.application.Util;
import br.unitins.projetoFarmacia.model.ItemVenda;
import br.unitins.projetoFarmacia.model.Produto;
import br.unitins.projetoFarmacia.model.Usuario;
import br.unitins.projetoFarmacia.model.Venda;

@Named
@ViewScoped
public class CarrinhoController implements Serializable {

	private static final long serialVersionUID = -4539458199624453855L;

	private Venda venda;

	public Venda getVenda() {
		if (venda == null)
			venda = new Venda();

		// obtendo o carrinho da sessao
		@SuppressWarnings("unchecked")
		List<ItemVenda> carrinho = (ArrayList<ItemVenda>) Session.getInstance().getAttribute("carrinho");

		// adicionando os itens do carrinho na venda
		if (carrinho == null)
			carrinho = new ArrayList<ItemVenda>();
		venda.setListaItemVenda(carrinho);

		return venda;
	}

	
	public void remover(int idProduto) {
		
	}

	@SuppressWarnings("unused")
	private void limpar() {
		venda = null;
	}

	public void finalizar() {
	/*	// Definindo o usuario que está logado com o cliente
		Usuario user = (Usuario) Session.getInstance().getAttribute("usuarioLogado");
		getVenda().setUsuario(user.getNome());

		getVenda().setUsuario((Usuario) Session.getInstance().getAttribute("usuarioLogado"));
		VendaDAO dao = new VendaDAO();
		dao.create(getVenda());

		// Finalizando venda e limpando o carrinho
		@SuppressWarnings("unchecked")
		List<ItemVenda> carrinho = (List<ItemVenda>) Session.getInstance().getAttribute("carrinho");
		carrinho.clear(); */
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}
}
