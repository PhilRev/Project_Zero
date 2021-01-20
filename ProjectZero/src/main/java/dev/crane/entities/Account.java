package dev.crane.entities;

import java.text.DecimalFormat;

public class Account {

	private int account_id;			// Unique account number
	private int user_id;			// Who's account is it.
	private double balance;			// Account balance
	private String account_type;		// Account type. Saving, checking or Line of Credit.
	
	
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	public Account() {
		super();
	}

	public Account(int user_id, double balance, String account_type) {
		super();
		this.user_id = user_id;
		this.balance = balance;
		this.account_type = account_type;
	}

	public Account(int account_id, int user_id, double balance, String account_type) {
		super();
		this.account_id = account_id;
		this.user_id = user_id;
		this.balance = balance;
		this.account_type = account_type;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	@Override
	public String toString() {
		return "Account type: " + account_type + "		Account number: " + account_id + "     Account balance: $" + df.format(balance);
		
	}
	
	
	
}
