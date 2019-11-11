package br.unitins.projetoFarmacia.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.unitins.projetoFarmacia.model.Endereco;
import br.unitins.projetoFarmacia.model.Perfil;
import br.unitins.projetoFarmacia.model.Telefone;
import br.unitins.projetoFarmacia.model.Usuario;

public class UsuarioDAO extends DAO<Usuario> {

	public UsuarioDAO(Connection conn) {
		super(conn);
		
	}
	
	public UsuarioDAO() {
		super(null);
		
	}

	public Usuario login(String email, String senha) {
		
		Connection conn = getConnection();
		
		try {
			PreparedStatement stat = conn.prepareStatement(
					"SELECT " +
					"  idusuario, " +
					"  nome, " +
					"  email, " +
					"  senha, " +
					"  ativo, " +
					"  perfil " +					
					"FROM " +
					"  public.usuario " +
					"WHERE email = ? AND senha = ? ");
			
			stat.setString(1, email);
			stat.setString(2, senha);
			
			ResultSet rs = stat.executeQuery();
			
			Usuario usuario = null;
			
			if(rs.next()) {
				usuario = new Usuario();
				usuario.setTelefone(new Telefone());
				usuario.setEndereco(new Endereco());
				usuario.setIdUsuario(rs.getInt("idusuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setAtivo(rs.getBoolean("ativo"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
			}
			
			return usuario;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void create(Usuario usuario) throws SQLException {
		
		Connection  conn = getConnection();
		
		PreparedStatement stat = conn.prepareStatement(
				"INSERT INTO " +
			    "public.usuario " +
			    " (nome, email, senha, dataadmissao, ativo, perfil) " +
				"VALUES " +
			    " (?, ?, ?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);
		stat.setString(1, usuario.getNome());
		stat.setString(2, usuario.getEmail());
		stat.setString(3, usuario.getSenha());
		stat.setDate(4, (usuario.getDataAdmissao() == null ? null : java.sql.Date.valueOf(usuario.getDataAdmissao())));
		stat.setBoolean(5, usuario.getAtivo());
		stat.setInt(6, usuario.getPerfil().getValue());
		
		stat.execute();
		
		// obtendo o id gerado pela tabela do banco de dados
		ResultSet rs = stat.getGeneratedKeys();
		rs.next();
		usuario.getTelefone().setIdTelefone(rs.getInt("idusuario"));
		
		TelefoneDAO dao = new TelefoneDAO(conn);
		dao.create(usuario.getTelefone());

		usuario.getEndereco().setIdEndereco(rs.getInt("idusuario"));
		
		EnderecoDAO dao2 = new EnderecoDAO(conn);
		dao2.create(usuario.getEndereco());
		
	}

	@Override
	public void update(Usuario usuario) throws SQLException {
		
		Connection  conn = getConnection();
		
		PreparedStatement stat = conn.prepareStatement(
				"UPDATE public.usuario SET " +
			    " nome = ?, " +
			    " email = ?, " +
			    " senha = ?, " +
			    " dataadmissao = ?, " +
			    " ativo = ?, " +
			    " perfil = ? " +
				"WHERE " +
			    " idusuario = ? ");
		stat.setString(1, usuario.getNome());
		stat.setString(2, usuario.getEmail());
		stat.setString(3, usuario.getSenha());
		stat.setDate(4, (usuario.getDataAdmissao() == null ? null : java.sql.Date.valueOf(usuario.getDataAdmissao())));
		stat.setBoolean(5, usuario.getAtivo());
		stat.setInt(6, usuario.getPerfil().getValue());
		stat.setInt(7, usuario.getIdUsuario());
			
		stat.execute();
			
		TelefoneDAO dao = new TelefoneDAO(conn);
		dao.update(usuario.getTelefone());

		EnderecoDAO dao2 = new EnderecoDAO(conn);
		dao2.update(usuario.getEndereco());
				
	}

	@Override
	public void delete(int id) throws SQLException {

		Connection  conn = getConnection();
		// deletando o telefone (pq possui um relacionamento de fk)
		// passando o conn para manter a mesma transacao
		
		TelefoneDAO dao = new TelefoneDAO(conn);
		dao.delete(id);
		
		// telefone tem um relecionamento 1 pra 1, ou seja, o id do usuario eh o mesmo do telefone.
		EnderecoDAO dao2 = new EnderecoDAO(conn);
		dao2.delete(id);
		// endereco tem um relecionamento 1 pra 1, ou seja, o id do usuario eh o mesmo do endereco.
		
		// deletando o usuario
		PreparedStatement stat = conn.prepareStatement(
				"DELETE FROM public.usuario WHERE idusuario = ?");
		stat.setInt(1, id);
		
		stat.execute();	
	}
	
	@Override
	public List<Usuario> findAll() {
		Connection conn = getConnection();
		if (conn == null) 
			return null;
		
		try {
			PreparedStatement stat = conn.prepareStatement(
					"SELECT " +
					"  idusuario, " +
					"  nome, " +
					"  email, " +
					"  senha, " +
					"  dataadmissao, " +
					"  ativo, " +
					"  perfil " +					
					"FROM " +
					"  public.usuario ");
			ResultSet rs = stat.executeQuery();
			
			List<Usuario> listaUsuario = new ArrayList<Usuario>();
			
			while(rs.next()) {
				Usuario usuario = new Usuario();
			
				usuario.setIdUsuario(rs.getInt("idusuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
				//usuario.setDataAdmissao(rs.getDate(java.sql.Date.valueOf("dataadmissao")));
				usuario.setAtivo(rs.getBoolean("ativo"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				
				listaUsuario.add(usuario);
			}
			
			if (listaUsuario.isEmpty())
				return null;
			return listaUsuario;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Usuario findById(Integer id) {
		Connection conn = getConnection();
		if (conn == null) 
			return null;
		
		try {
			PreparedStatement stat = conn.prepareStatement(
					"SELECT " +
					"  idusuario, " +
					"  nome, " +
					"  email, " +
					"  senha, " +
					"  ativo, " +
					"  perfil " +					
					"FROM " +
					"  public.usuario " +
					"WHERE idusuario = ? ");
			
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			
			Usuario usuario = null;
			
			if(rs.next()) {
				usuario = new Usuario();
				usuario.setIdUsuario(rs.getInt("idusuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setAtivo(rs.getBoolean("ativo"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				
				
				TelefoneDAO dao = new TelefoneDAO(conn);
				usuario.setTelefone(dao.findById(id));
				
				EnderecoDAO dao2 = new EnderecoDAO(conn);
				usuario.setEndereco(dao2.findById(id));
				// caso o retorno do telefone seja nulo, instanciar um telefone
				if (usuario.getTelefone() == null)
					usuario.setTelefone(new Telefone());
				// caso o retorno do endereco seja nulo, instanciar um endereco
				if (usuario.getEndereco() == null)
					usuario.setEndereco(new Endereco());
			}
			
			return usuario;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Usuario> findByNome(String nome) {
		Connection conn = getConnection();
		if (conn == null) 
			return null;
		
		try {
			PreparedStatement stat = conn.prepareStatement(
					"SELECT " +
					"  u.idusuario, " +
					"  u.nome, " +
					"  u.email, " +
					"  u.senha, " +
					"  u.dataadmissao, " +
					"  u.ativo, " +
					"  u.perfil, " +
					"  e.residencia, " +
					"  e.estado, " +
					"  e.cidade, " +
					"  e.cep, " +
					"  t.codigoarea, " +
					"  t.numero " +
					"  FROM " +
					"  usuario u, endereco e, telefone t " +
					"  WHERE " +
					"  u.idusuario = e.idendereco and u.idusuario = t.idtelefone and u.nome ilike ? ");
			
			stat.setString(1, nome == null ? "%" : "%"+nome+"%");
			ResultSet rs = stat.executeQuery();
			
			List<Usuario> listaUsuario = new ArrayList<Usuario>();
			
			while(rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setEndereco(new Endereco());
				usuario.setTelefone(new Telefone());
				usuario.setIdUsuario(rs.getInt("idusuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setDataAdmissao(rs.getDate("dataadmissao") == null ? null : (rs.getDate("dataadmissao").toLocalDate()));
				usuario.setAtivo(rs.getBoolean("ativo"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				usuario.getEndereco().setResidencia(rs.getString("residencia"));
				usuario.getEndereco().setEstado(rs.getString("estado"));
				usuario.getEndereco().setCidade(rs.getString("cidade"));
				usuario.getEndereco().setCep(rs.getString("cep"));
				usuario.getTelefone().setCodigoArea(rs.getString("codigoarea"));
				usuario.getTelefone().setNumero(rs.getString("numero"));
				
				listaUsuario.add(usuario);
			}
			
			if (listaUsuario.isEmpty())
				return null;
			return listaUsuario;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}