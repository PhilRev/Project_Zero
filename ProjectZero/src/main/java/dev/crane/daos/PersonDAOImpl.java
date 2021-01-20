package dev.crane.daos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.crane.entities.Person;

public class PersonDAOImpl implements PersonDAO {

	private static Map<Integer,Person> userTable = new HashMap<Integer, Person>();
	private static int idMaker = 0;
	
	public boolean createUser(Person user) {
		// Unique user ID. 
		user.setUser_id(++idMaker);
		userTable.put(idMaker, user);
		return true;
	}

	public Person getUserById(int user_id) {
		Person user = userTable.get(user_id);
		return user;
	}

	public List<Person> getAllUsers() {
		List<Person> users = new ArrayList<Person>(userTable.values());
		return users;
	}

	public boolean updateUser(Person user) {
		int user_id = user.getUser_id();
		userTable.put(user_id, user);
		return true;
	}

	public boolean deleteUser(int user_id) {
		userTable.remove(user_id);
		return true;
	}
	
}
