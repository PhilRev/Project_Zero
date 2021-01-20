package dev.crane.entities;

import java.util.Date;

public class Transaction {
	
	private Date date_in;
	private int account_id;
	private int user_id;
	private String account_type;
	private String trans_type;
	private double amount;
	
	public Transaction() {
		super();
	}

	public Transaction(int account_id, int user_id, String account_type, String trans_type, double amount) {
		super();
		this.account_id = account_id;
		this.user_id = user_id;
		this.account_type = account_type;
		this.trans_type = trans_type;
		this.amount = amount;
	}

	public Transaction(Date date_in, int account_id, int user_id, String account_type, String trans_type,
			double amount) {
		super();
		this.date_in = date_in;
		this.account_id = account_id;
		this.user_id = user_id;
		this.account_type = account_type;
		this.trans_type = trans_type;
		this.amount = amount;
	}

	public Date getDate_in() {
		return date_in;
	}

	public void setDate_in(Date date_in) {
		this.date_in = date_in;
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

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public String getTrans_type() {
		return trans_type;
	}

	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return  date_in + " |      " + account_id + "  |        " + user_id + " | " + account_type + " | " + trans_type + " | " + amount ;
	}

}
