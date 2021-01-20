package dev.crane.entities;

// Common information to be shared by both customer (users) and managers (super users).
public class Person {

	private int user_id = 0;	// User ID number
	private String user_type;	// user_type of user Super or reg.
	private String fName;		// User's first name.
	private String lName;		// User's last name.
	private String username;	// User's user name.
	private String user_pass;	// User's user_pass.
	
	// No argument constructor.
	public Person() {
		super();
	}
	
	public Person(String user_type, String fName, String lName, String username, String user_pass) {
		super();
		this.user_type = user_type;
		this.fName = fName;
		this.lName = lName;
		this.username = username;
		this.user_pass = user_pass;
	}

	// Overloaded constructor.
	public Person(int user_id, String user_type, String fName, String lName, String username, String user_pass) {
		super();
		this.user_id = user_id;
		this.user_type = user_type;
		this.fName = fName;
		this.lName = lName;
		this.username = username;
		this.user_pass = user_pass;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUser_pass() {
		return user_pass;
	}

	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}

	@Override
	public String toString() {
		return "Customer ID: " + user_id + "     Name: " + fName + " " + lName + "     user_pass: " + user_pass;
	}
	
}
