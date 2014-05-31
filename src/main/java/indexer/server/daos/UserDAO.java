package main.java.indexer.server.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		if(user.getEmail() == null)
			user.setEmail("");
		String sql = "INSERT INTO users "
				+ "(username,password,lastname,firstname,email,indexedrecords) VALUES (?,?,?,?,?,?)";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setString(1,user.getUserName());
			statement.setString(2,user.getPassword());
			statement.setString(3,user.getLastName());
			statement.setString(4,user.getFirstName());
			statement.setString(5,user.getEmail());
			statement.setInt(6,user.getIndexedRecords());
			if(statement.executeUpdate() == 1){
				ResultSet resultSet = statement.getGeneratedKeys();
				if(resultSet.next())
					user.setUserId(resultSet.getInt(1));
			}else{
				database.error();
			}
		}catch(SQLException e){
			database.error();
			//e.printStackTrace();
		}
		return user;
	}
	
	//READ
	
	/**
	 * Reads from the database and returns the user with the given username and password.
	 * @param userName the username of the user to be read from the database.
	 * @param password the password of the user to be read from the database.
	 * @return the user with the given username and password.
	 */
	public User readUser(String userName, String password){
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		User user = new User();
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setString(1,userName);
			statement.setString(2,password);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				user.setUserId(rs.getInt("userid"));
				user.setUserName(rs.getString("userName"));
				user.setPassword(rs.getString("password"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setEmail(rs.getString("email"));
				user.setIndexedRecords(rs.getInt("indexedrecords"));
				user.setCurrentBatch(rs.getInt("currentbatch"));
			}else{
				return null;
			}
		}catch(SQLException e){
			database.error();
			//e.printStackTrace();
		}
		return user;
	}
	
	//UPDATE
	
	/**
	 * Updates the given user in the database.
	 * @param user the user to be updated.
	 * @return the User that has been updated.
	 */
	public User updateUser(User user){
		if(user.getEmail() == null)
			user.setEmail("");
		String sql = "UPDATE users SET username=?,password=?,lastname=?,firstname=?,email=?,indexedrecords=?,currentBatch=? "
				+ "WHERE userid=?";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setString(1,user.getUserName());
			statement.setString(2,user.getPassword());
			statement.setString(3,user.getLastName());
			statement.setString(4,user.getFirstName());
			statement.setString(5,user.getEmail());
			statement.setInt(6,user.getIndexedRecords());
			statement.setInt(7,user.getCurrentBatch());
			statement.setInt(8,user.getUserId());
			statement.executeUpdate();
		}catch(SQLException e){
			database.error();
			//e.printStackTrace();
		}
		return user;
	}
	
	//DELETE
	
	/**
	 * Delete the given user from the database.
	 * @param user the user to be deleted.
	 */
	public void deleteUser(User user){
		String sql = "DELETE FROM users WHERE userid=?";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,user.getUserId());
			statement.executeUpdate();
		}catch(SQLException e){
			database.error();
			//e.printStackTrace();
		}
	}

	/**
	 * Delete the user with the given username and password from the database.
	 * @param userName the username of the user to be deleted.
	 * @param password the password of the user to be deleted.
	 */
	public void deleteUser(String userName, String password){
		String sql = "DELETE FROM users WHERE username=? AND password=?";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setString(1,userName);
			statement.setString(2,password);
			statement.executeUpdate();
		}catch(SQLException e){
			database.error();
			//e.printStackTrace();
		}
	}
	
}
