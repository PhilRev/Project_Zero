package dev.crane.daos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import dev.crane.entities.Account;


public class AccountDAOImpl implements AccountDAO{

	// HashMaps to be replaced by SQL
	private static List<Account> accountTable = new ArrayList<Account>();
	private static Iterator<Account> it = accountTable.iterator();
	private static int idMaker = 0;

	public boolean createAccount(Account account) {
		account.setAccount_id(++idMaker);
		accountTable.add(account);
		return true;
	}

	public Account getAccountByAccountID(int account_id) {
		it = accountTable.iterator();
		Account acc = null;
		while(it.hasNext()) {
			acc = it.next();
			if (acc.getAccount_id() == account_id) {
				return acc;
			}
		}
		return acc;
	}

	public List<Account> getAccountsByUserId(int user_id) {
		List<Account> out = new ArrayList<Account>();
		it = accountTable.iterator();
		while(it.hasNext()) {
			Account temp = it.next();
			if(temp.getUser_id()==user_id) {
				out.add(temp);
			}
		}
		return out;
	}

	public List<Account> getAllAccounts() {
		List<Account> accounts = new ArrayList<Account>(accountTable);
		return accounts;
	}

	public boolean updateAccount(Account account) {
		int account_id = account.getAccount_id();
		it = accountTable.iterator();
		while(it.hasNext()) {
			Account temp = it.next();
			if(temp.getAccount_id() == account_id) {
				accountTable.remove(temp);
				accountTable.add(account);
				return true;
			}
		}
		return false;
	}

	public boolean deleteAccount(int account_id) {
		it = accountTable.iterator();
		while(it.hasNext()) {
			Account temp = it.next();
			if(temp.getAccount_id() == account_id) {
				accountTable.remove(temp);
				return true;
			}
		}
		return false;
	}

	public boolean deleteAllAcounts() {
		accountTable.clear();
		return true;
	}

}
