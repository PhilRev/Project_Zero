package dev.crane.service;

import java.util.List;

import dev.crane.daos.TransactionDAO;
import dev.crane.daos.TransactionDAOSQLImpl;
import dev.crane.entities.Transaction;

public class TransactionServiceImpl implements TransactionService {
	
	private static TransactionDAO transDao = new TransactionDAOSQLImpl();

	public boolean createTransaction(int account_id, int user_id, String account_type, String trans_type, double amount) {
		Transaction trans = new Transaction(account_id, user_id, account_type, trans_type, amount);
		return transDao.createTransaction(trans);
	}

	public List<Transaction> getTransactionsByAccountID(int account_id) {
		return transDao.getTransByAccID(account_id);
	}

	public List<Transaction> getTransactionsByUserID(int user_id) {
		return transDao.getTransByUserID(user_id);
	}

	public List<Transaction> getAllTransactions() {
		return transDao.getAllTrans();
	}

}
