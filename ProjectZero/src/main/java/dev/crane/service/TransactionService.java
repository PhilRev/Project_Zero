package dev.crane.service;

import java.util.List;

import dev.crane.entities.Transaction;

public interface TransactionService {

	boolean createTransaction(int account_id, int user_id, String account_type, String trans_type, double amount);
	
	List<Transaction> getTransactionsByAccountID(int account_id);
	
	List<Transaction> getTransactionsByUserID(int user_id);
	
	List<Transaction> getAllTransactions();
	
	/* This is an archive it will only have create transaction and read 
	 * transaction capabilities.
	 */
	
}
