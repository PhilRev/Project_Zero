package dev.crane.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.crane.entities.Account;
import dev.crane.util.JDBCConnection;

public class AccountDAOSQLImpl implements AccountDAO {

	private static Connection conn = JDBCConnection.getConnected();
	private static String sql = "";
	
	public boolean createAccount(Account account) {
		try {
			sql = "CALL add_account(?,?,?)";
			CallableStatement cs = conn.prepareCall(sql);
			
			cs.setString(1, Integer.toString(account.getUser_id()));
			cs.setString(2, Double.toString(account.getBalance()));
			cs.setString(3, account.getAccount_type());
			
			cs.execute();
			return true;
			
		} catch (SQLException e) {
			e.getStackTrace();	
		}
		return false;
	}

	public Account getAccountByAccountID(int account_id) {
		try {
			sql = "SELECT * FROM account WHERE account_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(account_id));
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				Account acc = new Account();
				acc.setAccount_id(rs.getInt("ACCOUNT_ID"));
				acc.setUser_id(rs.getInt("USER_ID"));
				acc.setBalance(rs.getDouble("BALANCE"));
				acc.setAccount_type(rs.getString("ACCOUNT_TYPE"));
				return acc;
			}
			
		} catch (SQLException e) {
			e.getStackTrace();	
		}
		return null;
	}

	public List<Account> getAccountsByUserId(int user_id) {
		List<Account> accounts = new ArrayList<Account>();
		try {
			sql = "SELECT * FROM account WHERE user_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, Integer.toString(user_id));
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				accounts.add(new Account(rs.getInt("ACCOUNT_ID"),rs.getInt("USER_ID"),rs.getDouble("BALANCE"),rs.getString("ACCOUNT_TYPE")));
			}
			return accounts;
			
		} catch (SQLException e) {
			e.getStackTrace();	
		}
		return null;
	}

	public List<Account> getAllAccounts() {
		List<Account> accounts = new ArrayList<Account>();
		try {
			sql = "SELECT * FROM account";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				accounts.add(new Account(rs.getInt("ACCOUNT_ID"),rs.getInt("USER_ID"),rs.getDouble("BALANCE"),rs.getString("ACCOUNT_TYPE")));
			}
			return accounts;
			
		} catch (SQLException e) {
			e.getStackTrace();	
		}
		return null;
	}

	public boolean updateAccount(Account account) {
		try {
			
			sql = "UPDATE account SET user_id = ?, balance = ?, account_type = ? WHERE account_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, Integer.toString(account.getUser_id()));
			ps.setString(2, Double.toString(account.getBalance()));
			ps.setString(3, account.getAccount_type());
			ps.setString(4, Integer.toString(account.getAccount_id()));

			ps.executeQuery();
			return true;
			
		} catch (SQLException e) {
			e.getStackTrace();	
		}
		return false;
	}

	public boolean deleteAccount(int account_id) {
		try {
			
			sql = "DELETE account WHERE account_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, account_id);
			ps.execute();
			return true;
			
		} catch (SQLException e) {
			e.getStackTrace();	
		}
		return false;
	}

	public boolean deleteAllAcounts() {
		try {
			sql = "TRUNCATE TABLE account";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute();
			return true;
			
		} catch (SQLException e) {
			e.getStackTrace();	
		}
		return false;
	}

}
