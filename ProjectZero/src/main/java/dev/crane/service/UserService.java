package dev.crane.service;

import java.util.List;

import dev.crane.entities.Person;

public interface UserService {
	
	// Register a new user.
	boolean registerUser(String user_type, String fName, String lName, String username, String user_pass);

	Person getUserByID(int user_id);
	
	// Need to see all users for login information.
	List<Person> viewAllUsers();
	
	boolean updateUser(Person user);

	// Any user_type user can delete a user account.
	boolean deleteUser(int user_id);
	
	boolean deleteAllUsers();
	
}
