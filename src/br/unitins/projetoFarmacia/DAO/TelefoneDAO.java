package br.unitins.projetoFarmacia.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.projetoFarmacia.model.Telefone;

public class TelefoneDAO extends DAO<Telefone> {

	public TelefoneDAO(Connection conn) {
		super(conn);
	}

	@Override
	public void create(Telefone telefone) throws SQLException {
		
		Connection  conn = getConnection();
		
		PreparedStatement stat = conn.prepareStatement(
				"INSERT INTO " +
			    "public.telefone " +
			    " (idtelefone, codigoarea, numero) " +
				"VALUES " +
			    " (?, ?, ?) ");
		stat.setInt(1, telefone.getIdTelefone());
		stat.setString(2, telefone.getCodigoArea());
		stat.setString(3, telefone.getNumero());
		
		stat.execute();
		stat.close();
			
	}

	@Override
	public void update(Telefone telefone) throws SQLException {
		Connection  conn = getConnection();
		
		PreparedStatement stat = conn.prepareStatement(
				"UPDATE public.telefone SET " +
			    " codigoarea = ?, " +
			    " numero = ? " +
				"WHERE " +
			    " idtelefone = ? ");
		stat.setString(1, telefone.getCodigoArea());
		stat.setString(2, telefone.getNumero());
		stat.setInt(3, telefone.getIdTelefone());
		
			
		stat.execute();
	}

	@Override
	public void delete(int id) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement(
				"DELETE FROM public.telefone WHERE idtelefone =  ?");
		stat.setInt(1, id);

		stat.execute();
		stat.close();
	}

	@Override
	public List<Telefone> findAll() {
		Connection conn = getConnection();
		if (conn == null) 
			return null;
		
		try {
			PreparedStatement stat = conn.prepareStatement(
					"SELECT " +
					"  idtelefone, " +
					"  codigoarea, " +
					"  numero " +
					"FROM " +
					"  public.telefone ");
			ResultSet rs = stat.executeQuery();
			
			List<Telefone> listaTelefone = new ArrayList<Telefone>();
			
			while(rs.next()) {
				
				Telefone telefone = new Telefone();
				telefone.setIdTelefone(rs.getInt("idtelefone"));
				telefone.setCodigoArea(rs.getString("codigoarea"));
				telefone.setNumero(rs.getString("numero"));
				
				listaTelefone.add(telefone);
			}
			
			if (listaTelefone.isEmpty())
				return null;
			return listaTelefone;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Telefone findById(Integer id) {
		Connection conn = getConnection();
		if (conn == null) 
			return null;
		
		try {
			PreparedStatement stat = conn.prepareStatement(
					"SELECT " +
					"  idtelefone, " +
					"  codigoarea, " +
					"  numero " +
					"FROM " +
					"  public.telefone " +
					"WHERE idtelefone = ? ");
			
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			
			Telefone telefone = null;
			
			if(rs.next()) {
				telefone = new Telefone();
				telefone.setIdTelefone(rs.getInt("idtelefone"));
				telefone.setCodigoArea(rs.getString("codigoarea"));
				telefone.setNumero(rs.getString("numero"));
			}
			
			return telefone;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
