package dev.crane.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dev.crane.daos.AccountDAO;
import dev.crane.daos.AccountDAOSQLImpl;
import dev.crane.daos.PersonDAO;
import dev.crane.daos.PersonDAOSQLImpl;
import dev.crane.entities.Account;
import dev.crane.entities.Person;

public class UserServiceImpl implements UserService {

	private static AccountDAO accDao = new AccountDAOSQLImpl();
	private static PersonDAO perDao = new PersonDAOSQLImpl();
	
	public boolean registerUser(String user_type, String fName, String lName, String username, String user_pass) {
		Person user = new Person(user_type, fName, lName, username, user_pass);
		return perDao.createUser(user);
	}

	public Person getUserByID(int user_id) {
		return perDao.getUserById(user_id);
	}

	public List<Person> viewAllUsers() {
		return perDao.getAllUsers();
	}

	public boolean updateUser(Person user) {
		return perDao.updateUser(user);
	}

	public boolean deleteUser(int user_id) {
		List<Account> accSet = accDao.getAccountsByUserId(user_id);
		if (!accSet.isEmpty()) {
			System.out.println("User still has open accounts.");
			return false;
		}
		return perDao.deleteUser(user_id);
	}

	public boolean deleteAllUsers() {
		boolean isTrue = false;
		List<Person> all = new ArrayList<Person>();
		all = viewAllUsers();
		Iterator<Person> it = all.iterator();
		while(it.hasNext()) {
			Person temp = it.next();
			if (temp.getUser_id() != 0) {
				isTrue = perDao.deleteUser(temp.getUser_id());
				if(!isTrue) {
					System.out.println("Error deleting all users.");
					return isTrue;
				}
			}
		}
		
		return isTrue;
	}

}
