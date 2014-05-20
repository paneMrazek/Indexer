package main.java.indexer.shared.models;

/**
 * <p>A user is anyone who has an account with our indexer program.</p>
 *
 */
public class User {
	
	private int userId;
	private String userName;
	private String password;
	private String lastName;
	private String firstName;
	private String email;
	private int indexedRecords;
	
	/**
	 * @return the userId
	 */
	public int getUserId(){
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId){
		this.userId = userId;
	}
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
	/**
	 * @return the lastName
	 */
	public String getLastName(){
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName(){
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	/**
	 * @return the email
	 */
	public String getEmail(){
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email){
		this.email = email;
	}
	/**
	 * @return the indexedRecords
	 */
	public int getIndexedRecords(){
		return indexedRecords;
	}
	/**
	 * @param indexedRecords the indexedRecords to set
	 */
	public void setIndexedRecords(int indexedRecords){
		this.indexedRecords = indexedRecords;
	}
	
	
	
	

}
