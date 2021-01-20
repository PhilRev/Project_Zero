package dev.crane.service;

import java.util.List;

import dev.crane.entities.Account;

public interface AccountService {

	// Any user_type user can create an account.
	boolean createAccount(int user_id, double balance, String account_type);
	
	Account getAccountByAccountID(int account_id);
	
	// Any user_type of user can view a specific users accounts own existing accounts and balances.
	List<Account> viewUserAccounts(int user_id);
	
	// A Super User can view all accounts.
	List<Account> viewAllAccounts();

	// Any user should be able to update accounts.
	boolean updateAccount(Account account);
	
	// Any user_type user can delete an account if it is empty.
	boolean deleteAccount(int account_id);
	
	// Only super users can delete all accounts.
	boolean deleteAllAcounts();
	
	/* Any user_type user can deposit or withdraw from an account.
	 * A user can execute multiple deposits or withdrawals in a session. 
	 */
	boolean deposit(double amount, int account_id);
	
	boolean withdraw(double amount, int account_id);

}