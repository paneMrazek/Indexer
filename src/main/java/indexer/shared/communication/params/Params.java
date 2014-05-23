package main.java.indexer.shared.communication.params;

/**
 * A class with the username and password used as the base Params class.
 *
 */
public abstract class Params{
	
	private String userName;
	private String password;
	
	/**
	 * @return the userName
	 */
	public String getUserName(){
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName){
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword(){
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password){
		this.password = password;
	}	
}