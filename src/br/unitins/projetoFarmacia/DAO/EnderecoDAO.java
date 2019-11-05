package br.unitins.projetoFarmacia.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.projetoFarmacia.model.Endereco;

public class EnderecoDAO extends DAO<Endereco> {

	public EnderecoDAO(Connection conn) {
		super(conn);
		
	}

	@Override
	public void create(Endereco endereco) throws SQLException {
		Connection  conn = getConnection();
		
		PreparedStatement stat = conn.prepareStatement(
				"INSERT INTO " +
			    "public.endereco " +
			    " (idendereco, residencia, estado, cidade, cep) " +
				"VALUES " +
			    " (?, ?, ?, ?, ?) ");
		stat.setInt(1, endereco.getIdEndereco());
		stat.setString(2, endereco.getResidencia());
		stat.setString(3, endereco.getEstado());
		stat.setString(4, endereco.getCidade());
		stat.setString(5, endereco.getCep());
		
		stat.execute();
		stat.close();
		
	}

	@Override
	public void update(Endereco endereco) throws SQLException {
		Connection  conn = getConnection();
		
		PreparedStatement stat = conn.prepareStatement(
				"UPDATE public.endereco SET " +
			    " residencia = ?, " +
			    " estado = ?, " +
			    " cidade = ?, " +
			    " cep = ? " +
				"WHERE " +
			    " idendereco = ? ");
		stat.setString(1, endereco.getResidencia());
		stat.setString(2, endereco.getEstado());
		stat.setString(3, endereco.getCidade());
		stat.setString(4, endereco.getCep());
		stat.setInt(5, endereco.getIdEndereco());
		
			
		stat.execute();
		
	}

	@Override
	public void delete(int id) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement(
				"DELETE FROM public.endereco WHERE idendereco =  ?");
		stat.setInt(1, id);

		stat.execute();
		stat.close();
	}

	@Override
	public List<Endereco> findAll() {
		Connection conn = getConnection();
		if (conn == null) 
			return null;
		
		try {
			PreparedStatement stat = conn.prepareStatement(
					"SELECT " +
					"  idendereco, " +
					"  residencia, " +
					"  estado, " +
					"  cidade, " +
					"  cep " +
					"FROM " +
					"  public.endereco ");
			ResultSet rs = stat.executeQuery();
			
			List<Endereco> listaEndereco = new ArrayList<Endereco>();
			
			while(rs.next()) {
				
				Endereco endereco = new Endereco();
				endereco.setIdEndereco(rs.getInt("idendereco"));
				endereco.setResidencia(rs.getString("residencia"));
				endereco.setEstado(rs.getString("estado"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setCep(rs.getString("cep"));
				
				listaEndereco.add(endereco);
			}
			
			if (listaEndereco.isEmpty())
				return null;
			return listaEndereco;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Endereco findById(Integer id) {
		Connection conn = getConnection();
		if (conn == null) 
			return null;
		
		try {
			PreparedStatement stat = conn.prepareStatement(
					"SELECT " +
					"  idendereco, " +
					"  residencia, " +
					"  estado, " +
					"  cidade, " +
					"  cep " +
					"FROM " +
					"  public.endereco " +
					"WHERE idendereco = ? ");
			
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			
			Endereco endereco = null;
			
			if(rs.next()) {
				endereco = new Endereco();
				endereco.setIdEndereco(rs.getInt("idendereco"));
				endereco.setResidencia(rs.getString("residencia"));
				endereco.setEstado(rs.getString("estado"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setCep(rs.getString("cep"));
			}
			
			return endereco;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
