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
		String sql = "INSERT INTO users "
				+ "(username,password,lastname,firstname,email) VALUES (?,?,?,?,?)";
		try(PreparedStatement ps = database.getConnection().prepareStatement(sql)){
			ps.setString(1,user.getUserName());
			ps.setString(2,user.getPassword());
			ps.setString(3,user.getLastName());
			ps.setString(4,user.getFirstName());
			if(user.getEmail() == null)
				user.setEmail("");
			ps.setString(5,user.getEmail());
			if(ps.executeUpdate() == 1){
				
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
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
		try(PreparedStatement ps = database.getConnection().prepareStatement(sql)){
			ps.setString(1,userName);
			ps.setString(2,password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				user.setUserId(rs.getInt("userid"));
				user.setUserName(rs.getString("userName"));
				user.setPassword(rs.getString("password"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setEmail(rs.getString("email"));
				user.setIndexedRecords(rs.getInt("indexedrecords"));
			}else{
				return null;
			}
		}catch(SQLException e){
			e.printStackTrace();
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
		String sql = "UPDATE users SET username=?,password=?,lastname=?,firstname=?,email=?,indexedrecords=? "
				+ "WHERE userid=?";
		try(PreparedStatement ps = database.getConnection().prepareStatement(sql)){
			ps.setString(1,user.getUserName());
			ps.setString(2,user.getPassword());
			ps.setString(3,user.getLastName());
			ps.setString(4,user.getFirstName());
			ps.setString(5,user.getEmail());
			ps.setInt(6,user.getIndexedRecords());
			ps.setInt(7,user.getUserId());
			ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
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
		try(PreparedStatement ps = database.getConnection().prepareStatement(sql)){
			ps.setInt(1,user.getUserId());
			ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	/**
	 * Delete the user with the given username and password from the database.
	 * @param userName the username of the user to be deleted.
	 * @param password the password of the user to be deleted.
	 */
	public void deleteUser(String userName, String password){
		String sql = "DELETE FROM users WHERE username=? AND password=?";
		try(PreparedStatement ps = database.getConnection().prepareStatement(sql)){
			ps.setString(1,userName);
			ps.setString(2,password);
			ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
