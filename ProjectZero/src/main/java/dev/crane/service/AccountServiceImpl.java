package dev.crane.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dev.crane.daos.AccountDAO;
import dev.crane.daos.AccountDAOSQLImpl;
import dev.crane.entities.Account;

public class AccountServiceImpl implements AccountService {
	
	private static AccountDAO accDao = new AccountDAOSQLImpl();
	private static TransactionService tranServ = new TransactionServiceImpl();
	private static List<Account> acc = new ArrayList<Account>();
	private static boolean isTrue = false;
	private static Iterator<Account> accIt = acc.iterator();
	
	public boolean createAccount(int user_id, double balance, String account_type) {
		isTrue = true;
		Account account = new Account(user_id, balance, account_type);
		Account acc1 = new Account();
		isTrue = accDao.createAccount(account);
		acc = accDao.getAllAccounts();
		accIt = acc.iterator();
		while(accIt.hasNext()) {
			account = accIt.next();
			if (account.getUser_id() == user_id && account.getAccount_type().equals(account_type)) {
				acc1 = account;
			}
		}
		isTrue = tranServ.createTransaction(acc1.getAccount_id(), user_id, account_type, "Created", 0.00);
		return isTrue;
	}

	public Account getAccountByAccountID(int account_id) {
		return accDao.getAccountByAccountID(account_id);
	}

	public List<Account> viewUserAccounts(int user_id) {
		return accDao.getAccountsByUserId(user_id);
	}

	public List<Account> viewAllAccounts() {
		return accDao.getAllAccounts();
	}

	public boolean updateAccount(Account account) {
		return accDao.updateAccount(account);
	}

	public boolean deleteAccount(int account_id) {
		isTrue = true;
		Account acc = accDao.getAccountByAccountID(account_id);
		if (acc.getBalance() > 0) {
			System.out.println("Account ballance is not zero. \nPlease withdraw or transfer funds.");
			isTrue = false;
		} else {
			isTrue = accDao.deleteAccount(account_id);
		}
		if(isTrue) {
			isTrue = tranServ.createTransaction(account_id, acc.getUser_id(), acc.getAccount_type(), "Deleted", 0);
		}
		return isTrue;
	}

	public boolean deleteAllAcounts() {
		Account acc1 = new Account();
		boolean ret = true;
		acc = accDao.getAllAccounts();
		accIt = acc.iterator();
		while(accIt.hasNext()) {
			acc1 = accIt.next();
			isTrue = deleteAccount(acc1.getAccount_id());
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!isTrue) {
				ret = false;
			}
		}
		return ret;
	}

	public boolean deposit(double amount, int account_id) {
		isTrue = true;
		Account acc = accDao.getAccountByAccountID(account_id);
		acc.setBalance(acc.getBalance() + amount);
		isTrue = accDao.updateAccount(acc);
		if (isTrue) {
			isTrue = tranServ.createTransaction(account_id, acc.getUser_id(), acc.getAccount_type(), "Deposit", amount);
		}
		
		return isTrue;
	}

	public boolean withdraw(double amount, int account_id) {
		isTrue = true;
		Account acc = accDao.getAccountByAccountID(account_id);
		acc.setBalance(acc.getBalance() - amount);
		isTrue = accDao.updateAccount(acc);
		if (isTrue) {
			isTrue = tranServ.createTransaction(account_id, acc.getUser_id(), acc.getAccount_type(), "Withdrawal", amount);
		}
		
		return isTrue;
	}

}
