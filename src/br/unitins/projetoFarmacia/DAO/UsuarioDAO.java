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
			    " (nome, email, senha, ativo, perfil) " +
				"VALUES " +
			    " (?, ?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);
		stat.setString(1, usuario.getNome());
		stat.setString(2, usuario.getEmail());
		stat.setString(3, usuario.getSenha());
		stat.setBoolean(4, usuario.getAtivo());
		stat.setInt(5, usuario.getPerfil().getValue());
		
		stat.execute();
		
		// obtendo o id gerado pela tabela do banco de dados
		ResultSet rs = stat.getGeneratedKeys();
		rs.next();
		usuario.getTelefone().setIdTelefone(rs.getInt("idtelefone"));
		usuario.getEndereco().setIdEndereco(rs.getInt("idendereco"));
		
		TelefoneDAO dao = new TelefoneDAO(conn);
		dao.create(usuario.getTelefone());
		
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
			    " ativo = ?, " +
			    " perfil = ? " +
				"WHERE " +
			    " idusuario = ? ");
		stat.setString(1, usuario.getNome());
		stat.setString(2, usuario.getEmail());
		stat.setString(3, usuario.getSenha());
		stat.setBoolean(4, usuario.getAtivo());
		stat.setInt(5, usuario.getPerfil().getValue());
		stat.setInt(6, usuario.getIdUsuario());
			
		stat.execute();
		
	}

	@Override
	public void delete(int id) throws SQLException {

		Connection  conn = getConnection();
		// deletando o telefone (pq possui um relacionamento de fk)
		// passando o conn para manter a mesma transacao
		
		TelefoneDAO dao = new TelefoneDAO(conn);
		// telefone tem um relecionamento 1 pra 1, ou seja, o id do usuario eh o mesmo do telefone.
		EnderecoDAO dao2 = new EnderecoDAO(conn);
		// endereco tem um relecionamento 1 pra 1, ou seja, o id do usuario eh o mesmo do endereco.
		dao.delete(id);
		
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
				
				EnderecoDAO dao2 = new EnderecoDAO(conn);
				usuario.setEndereco(dao2.findById(id));
				
				TelefoneDAO dao = new TelefoneDAO(conn);
				usuario.setTelefone(dao.findById(id));
				
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
					"  idusuario, " +
					"  nome, " +
					"  email, " +
					"  senha, " +
					"  ativo, " +
					"  perfil " +
					"FROM " +
					"  public.usuario " +
					"WHERE " +
					"  nome ilike ? ");
			
			stat.setString(1, nome == null ? "%" : "%"+nome+"%");
			ResultSet rs = stat.executeQuery();
			
			List<Usuario> listaUsuario = new ArrayList<Usuario>();
			
			while(rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setIdUsuario(rs.getInt("idusuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
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

}