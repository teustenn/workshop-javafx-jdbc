package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	
	private Connection con;
	
	public DepartmentDaoJDBC(Connection con) {
		this.con = con;
	}

	@Override
	public void insert(Department obj) {
		
		PreparedStatement sta = null;
		
		try {
			sta = con.prepareStatement(
					"INSERT INTO department "
					+ "(Name) "
					+ "VALUES "
					+ "(?) ", 
					Statement.RETURN_GENERATED_KEYS);
			
			sta.setString(1, obj.getName());
			
			int rowsAffected = sta.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet res = sta.getGeneratedKeys();
				if (res.next()) {
					int id = res.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(res);
			}
			else {
				throw new DbException("Unexpected ERROR! No Rows Affected.");
			}
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatment(sta);
		}
	}

	@Override
	public void update(Department obj) {
		
		PreparedStatement sta = null;
		
		try {
			sta = con.prepareStatement(
					"UPDATE department "
					+ "SET Id = ?, Name = ? "
					+ "WHERE Id = ? ");
			
			sta.setInt(1, obj.getId());
			sta.setString(2, obj.getName());
			sta.setInt(3, obj.getId());
			
			sta.executeUpdate();
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatment(sta);
		}
	}

	@Override
	public void deleteById(Integer id) {
		
		PreparedStatement sta = null;
		
		try {
			sta = con.prepareStatement (
					"DELETE FROM department "
					+ "WHERE Id = ? ");
			
			sta.setInt(1, id);
			sta.executeUpdate();
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatment(sta);
		}
	}

	@Override
	public Department findbyId(Integer id) {
		
		PreparedStatement sta = null;
		ResultSet res = null;
		
		try {
			sta = con.prepareStatement (
					"SELECT * FROM department "
					+ "WHERE Id = ? ");
			
			sta.setInt(1, id);
			res = sta.executeQuery();
			
			if (res.next()) {
				Department obj =  new Department();
				obj.setId(res.getInt("Id"));
				obj.setName(res.getString("Name"));
				return obj;
				
			}
			return null;
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatment(sta);
			DB.closeResultSet(res);
		}
	}

	@Override
	public List<Department> findAll() {
		
		PreparedStatement sta = null;
		ResultSet res = null;
		
		try {
			sta = con.prepareStatement(
					"SELECT * FROM department "
					+ "ORDER BY Name ");
			
			res = sta.executeQuery();
			
			List<Department> list = new ArrayList<>();
			
			while (res.next()) {
				Department obj =  new Department();
				obj.setId(res.getInt("Id"));
				obj.setName(res.getString("Name"));
				list.add(obj);
				
			}
			return list;
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatment(sta);
			DB.closeResultSet(res);
		}
	}

}
