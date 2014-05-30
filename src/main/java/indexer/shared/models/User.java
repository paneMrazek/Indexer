package main.java.indexer.shared.models;

import java.io.Serializable;

/**
 * <p>A user is anyone who has an account with our indexer program.</p>
 *
 */
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + indexedRecords;
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + userId;
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		User other = (User) obj;
		if(email == null){
			if(other.email != null) return false;
		}else if(!email.equals(other.email)) return false;
		if(firstName == null){
			if(other.firstName != null) return false;
		}else if(!firstName.equals(other.firstName)) return false;
		if(indexedRecords != other.indexedRecords) return false;
		if(lastName == null){
			if(other.lastName != null) return false;
		}else if(!lastName.equals(other.lastName)) return false;
		if(password == null){
			if(other.password != null) return false;
		}else if(!password.equals(other.password)) return false;
		if(userId != other.userId) return false;
		if(userName == null){
			if(other.userName != null) return false;
		}else if(!userName.equals(other.userName)) return false;
		return true;
	}
	
	
	
	

}
