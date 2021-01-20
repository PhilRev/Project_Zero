package dev.crane.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.crane.entities.Person;
import dev.crane.util.JDBCConnection;

public class PersonDAOSQLImpl implements PersonDAO{
	
	private static Connection conn = JDBCConnection.getConnected();
	private static String sql = "";
	
	public boolean createUser(Person user) {
		try {
			sql = "Call add_person (?,?,?,?,?)";
			CallableStatement cs = conn.prepareCall(sql);
			
			cs.setString(1, user.getUser_type());
			cs.setString(2, user.getfName());
			cs.setString(3, user.getlName());
			cs.setString(4, user.getUsername());
			cs.setString(5, user.getUser_pass());
			
			cs.execute();
			
			return true;
			
		} catch (SQLException e) {
			e.getStackTrace();
		}
			
		
		return false;
	}

	public Person getUserById(int user_id) {
		try {
			sql = "SELECT * FROM person WHERE user_id = (?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(user_id));
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Person user = new Person(rs.getInt("USER_ID"),rs.getString("USER_TYPE"),rs.getString("FNAME"),rs.getString("LNAME"),rs.getString("USERNAME"),rs.getString("USER_PASS"));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Person> getAllUsers() {
		List<Person> users = new ArrayList<Person>();
		try {
			sql = "SELECT * FROM person";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Person user = new Person(rs.getInt("USER_ID"),rs.getString("USER_TYPE"),rs.getString("FNAME"),rs.getString("LNAME"),rs.getString("USERNAME"),rs.getString("USER_PASS"));
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean updateUser(Person user) {
		try {
			sql = "UPDATE person SET user_type = ?, fname = ?, lname = ?, username =?, user_pass =? WHERE user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, user.getUser_type());
			ps.setString(2, user.getfName());
			ps.setString(3, user.getlName());
			ps.setString(4, user.getUsername());
			ps.setString(5, user.getUser_pass());
			ps.setString(6, Integer.toString(user.getUser_id()));
			
			ps.executeQuery();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteUser(int user_id) {
		try {
			sql = "DELETE person WHERE user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user_id);
			ps.execute();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
