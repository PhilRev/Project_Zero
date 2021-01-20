package dev.crane.daos;

import java.util.List;

import dev.crane.entities.Transaction;

public interface TransactionDAO {

	// Create
	boolean createTransaction(Transaction trans);
	
	// Read
	List<Transaction> getTransByAccID(int account_id);
	List<Transaction> getTransByUserID(int user_id);
	List<Transaction> getAllTrans();

	/* Update and delete has been intentionally left off because
	 * this is an archive database. If I had more time I would 
	 * have included them to keep consistancy.
	 */
	// Update

	// Delete
}
