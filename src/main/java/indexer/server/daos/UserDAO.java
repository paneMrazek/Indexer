package main.java.indexer.server.daos;

import java.util.List;

import main.java.indexer.shared.models.User;

/**
 * <p> Used to access the User table in the database.  
 * Has all needed CRUD operations.</p>
 *
 */
public class UserDAO{
	
	private Database database;
	
	public UserDAO(Database database){
		this.database = database;
	}
	
	//CREATE
	
	/**
	 * Adds the given user to the database.
	 * @param user the user to be added to the database
	 * @return the new user if successful, otherwise null.
	 */
	public User createUser(User user){
		return null;
	}
	
	//READ
	
	/**
	 * Reads and returns all users from the database.
	 * @return a list of all users in the database.
	 */
	public List<User> readUsers(){
		return null;
	}
	
	/**
	 * Reads from the database and returns the user with the given username and password.
	 * @param userName the username of the user to be read from the database.
	 * @param password the password of the user to be read from the database.
	 * @return the user with the given username and password.
	 */
	public User readUser(String userName, String password){
		return null;
	}
	
	//UPDATE
	
	/**
	 * Updates the given user in the database.
	 * @param user the user to be updated.
	 * @return the User that has been updated.
	 */
	public User updateUser(User user){
		return null;
	}
	
	//DELETE
	
	/**
	 * Delete the given user from the database.
	 * @param user the user to be deleted.
	 */
	public void deleteUser(User user){
		
	}

	/**
	 * Delete the user with the given username and password from the database.
	 * @param userName the username of the user to be deleted.
	 * @param password the password of the user to be deleted.
	 */
	public void deleteUser(String userName, String password){
		
	}
	
}
