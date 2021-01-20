package dev.crane.daos;

import java.util.List;

import dev.crane.entities.Account;

// CRUD
public interface AccountDAO {
	
	// Create
	boolean createAccount(Account account);
	
	// Read	
	Account getAccountByAccountID(int account_id);
	List<Account> getAccountsByUserId(int user_id);
	List<Account> getAllAccounts();
	
	// Update	
	boolean updateAccount(Account account);
	
	// Delete
	boolean deleteAccount(int account_id);
	
	boolean deleteAllAcounts();
	
}
