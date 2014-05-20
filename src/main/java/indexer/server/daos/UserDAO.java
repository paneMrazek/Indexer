package main.java.indexer.server.daos;

import java.util.List;

import javax.sql.DataSource;

import main.java.indexer.shared.models.User;

/**
 * <p> Used to access the User table in the database.  
 * Has all needed CRUD operations.</p>
 *
 */
public class UserDAO{
	protected DataSource dataSource;
	
	public UserDAO(){
		
	}
	
	//CREATE
	
	/**
	 * Adds the given user to the database
	 * @param user the user to be added to the database
	 * @return Null if unsuccessful, otherwise the user with new userId
	 */
	public User createUser(User user){
		return null;
	}
	
	/**
	 * Creates a new user with the given fields inside the database
	 * @param userName the UserName for the new user
	 * @param password the hashed password for the new user
	 * @param lastName the last name of the new user
	 * @param firstName the first name of the new user
	 * @param email the email of the new user
	 * @return The newly created user
	 */
	public User createUser(String userName, String password, String lastName, String firstName, String email){
		return null;
	}
	
	//READ
	
	/**
	 * Reads and returns all users from the database
	 * @return A list of all users in the database
	 */
	public List<User> readUsers(){
		return null;
	}
	
	/**
	 * Reads from the database and returns the user with the given userId
	 * @param userId the userId given to query the database
	 * @return The user with the given userId
	 */
	public User readUser(String userId){
		return null;
	}
	
	/**
	 * Reads from the database and returns the user with the given username and password
	 * @param userName the username of the user to be read from the database
	 * @param password the hashed password of the user to be read from the database
	 * @return The user with the given Username and password
	 */
	public User readUser(String userName, String password){
		return null;
	}
	
	//UPDATE
	
	/**
	 * Updates the given user in the database
	 * @param user the user to be updated
	 * @return The User that has been updated
	 */
	public User updateUser(User user){
		return null;
	}
	
	//DELETE
	
	/**
	 * Delete the given user from the database
	 * @param user the user to be deleted
	 */
	public void deleteUser(User user){
		
	}
	
	/**
	 * Delete the user with the given userId from the database
	 * @param userId the userId of the user to be deleted
	 */
	public void deleteUser(String userId){
		
	}
	/**
	 * Delete the user with the given username and password from the database
	 * @param userName the username of the user to be deleted
	 * @param password the password of the user to be deleted
	 */
	public void deleteUser(String userName, String password){
		
	}
	
}
