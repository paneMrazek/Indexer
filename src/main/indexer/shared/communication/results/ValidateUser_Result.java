package main.indexer.shared.communication.results;

import main.indexer.shared.models.User;

/**
 * A class with the user that is returned from validateUser.
 *
 */
public class ValidateUser_Result extends Result{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private boolean valid;
	
	public User getUser(){
		return user;
	}
	public void setUser(User user){
		this.user = user;
	}
	public boolean isValid(){
		return valid;
	}
	public void setValid(boolean valid){
		this.valid = valid;
	}



	/* 
	 * 
	 */
	@Override
	public String toString(){
		if(!valid)
			return "FALSE\n";
		if(isError())
			return super.toString();
		else
			return "TRUE\n" + user.getFirstName() + "\n" + 
				user.getLastName() + "\n" + user.getIndexedRecords() + "\n";
	}
	
	
	
}
