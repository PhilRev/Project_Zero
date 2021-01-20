package dev.crane.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.crane.entities.Transaction;
import dev.crane.util.JDBCConnection;

public class TransactionDAOSQLImpl implements TransactionDAO {

	private static Connection conn = JDBCConnection.getConnected();
	private static String sql = "";
	
	public boolean createTransaction(Transaction trans) {
		try {
			sql = "CALL add_transaction(?,?,?,?,?)";
			CallableStatement cs = conn.prepareCall(sql);
			
			cs.setString(1, Integer.toString(trans.getAccount_id()));
			cs.setString(2, Integer.toString(trans.getUser_id()));
			cs.setString(3, trans.getAccount_type());
			cs.setString(4, trans.getTrans_type());
			cs.setString(5, Double.toString(trans.getAmount()));
			
			cs.execute();
			return true;
			
		} catch (SQLException e) {
			e.getStackTrace();
		}
		return false;
	}

	public List<Transaction> getTransByAccID(int account_id) {
		List<Transaction> trans = new ArrayList<Transaction>();
		try {
			sql = "SELECT * FROM transaction WHERE account_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, Integer.toString(account_id));
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				trans.add(new Transaction(rs.getDate("DATE_IN"),rs.getInt("ACCOUNT_ID"),rs.getInt("USER_ID"),rs.getString("ACCOUNT_TYPE"),rs.getString("TRANS_TYPE"),rs.getDouble("AMOUNT")));
			}
			return trans;
			
		} catch (SQLException e) {
			e.getStackTrace();
		}
		return null;
	}

	public List<Transaction> getTransByUserID(int user_id) {
		List<Transaction> trans = new ArrayList<Transaction>();
		try {
			sql = "SELECT * FROM transaction WHERE user_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, Integer.toString(user_id));
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				trans.add(new Transaction(rs.getDate("DATE_IN"),rs.getInt("ACCOUNT_ID"),rs.getInt("USER_ID"),rs.getString("ACCOUNT_TYPE"),rs.getString("TRANS_TYPE"),rs.getDouble("AMOUNT")));
			}
			return trans;
		} catch (SQLException e) {
			e.getStackTrace();
		}
		return null;
	}

	public List<Transaction> getAllTrans() {
		List<Transaction> trans = new ArrayList<Transaction>();
		try {
			sql = "SELECT * FROM transaction";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				trans.add(new Transaction(rs.getDate("DATE_IN"),rs.getInt("ACCOUNT_ID"),rs.getInt("USER_ID"),rs.getString("ACCOUNT_TYPE"),rs.getString("TRANS_TYPE"),rs.getDouble("AMOUNT")));
			}
			return trans;
		} catch (SQLException e) {
			e.getStackTrace();
		}
		return null;
	}

}
