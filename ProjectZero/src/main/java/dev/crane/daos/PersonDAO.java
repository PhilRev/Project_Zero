package dev.crane.daos;

import java.util.List;

import dev.crane.entities.Person;

public interface PersonDAO {

	// Create
	boolean createUser(Person user);
	
	// Read
	Person getUserById(int user_id);
	List<Person> getAllUsers();
	
	// Update
	boolean updateUser(Person user);
	
	// Delete
	boolean deleteUser(int user_id);
}
